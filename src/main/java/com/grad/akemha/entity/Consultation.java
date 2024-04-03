package com.grad.akemha.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "consultation_text", nullable = false)
    private String consultationText;

    @Column(name = "consultation_answer")
    private String consultationAnswer;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

//    private User doctor_id;

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
    //specialization_type
}
