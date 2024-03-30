package com.grad.akemha.entity;

import jakarta.persistence.*;
import lombok.*;

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

    //specialization_type
}
