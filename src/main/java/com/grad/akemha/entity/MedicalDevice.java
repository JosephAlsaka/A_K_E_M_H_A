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
@Table(name = "medical_device")
public class MedicalDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer count;

    @Column()
    private Integer reservedCount;

    @Column(nullable = false, unique = true)
    private String name;


    @Column(name = "image_url", nullable = false)
    private String imageUrl;

}
