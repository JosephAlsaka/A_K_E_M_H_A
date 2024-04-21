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
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_public_id", nullable = false)
    private String imagePublicId;
}
