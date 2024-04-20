package com.grad.akemha.entity;

import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.entity.enums.ConsultationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String title;

    @Column(name = "consultation_text", nullable = false)
    private String consultationText;

    @Column(name = "consultation_answer")
    private String consultationAnswer;

    @Enumerated(EnumType.STRING)
    @Column()
    private ConsultationStatus consultationStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultationType consultationType;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_answer_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAnswerTime;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id",nullable = false)
    private User beneficiary;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToMany
    @JoinColumn(name = "consultation_id")
    private List<Image> images;

    @OneToMany
    @JoinColumn(name = "consultation_id")
    private List<Message> messages;
}
