package com.grad.akemha.entity;

import com.grad.akemha.entity.enums.DeviceReservationStatus;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device_reservation")
public class DeviceReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "medical_device_id")
    private MedicalDevice MedicalDevice;

    @Column()
    private DeviceReservationStatus status;
}
