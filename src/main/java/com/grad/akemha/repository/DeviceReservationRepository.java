package com.grad.akemha.repository;

import com.grad.akemha.entity.DeviceReservation;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.DeviceReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceReservationRepository extends JpaRepository<DeviceReservation,Long> {
    List<DeviceReservation> findByUserAndStatus(User user, DeviceReservationStatus status);

    int countByUserAndStatus(User user, DeviceReservationStatus status);

    List<DeviceReservation> findByExpirationTimeBeforeAndStatusIsNull(LocalDateTime currentTime);

    List<DeviceReservation> findByUserAndStatusIn(User user, List<DeviceReservationStatus> statuses);



}
