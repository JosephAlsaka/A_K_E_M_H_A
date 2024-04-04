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
@Table(name = "specialization")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialization_type",unique = true,nullable = false)
    private String specializationType;

    @OneToMany(mappedBy = "specialization")
    private List<Consultation> consultations;
}
