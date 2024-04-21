package com.grad.akemha.component;

import com.grad.akemha.repository.DeviceReservationRepository;
import com.grad.akemha.repository.MedicalDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationStatusUpdater {

    //    @Autowired
//    private MedicalDeviceRepository medicalDeviceRepository;
    @Autowired
    private DeviceReservationRepository deviceReservationRepository;

//    @Scheduled(fixedDelay = 1000) // Runs every second, you can adjust the value according to your needs
//    public void updateCheckOutStatus() {
//        LocalDateTime currentTime = LocalDateTime.now();
//        List<ReservationHistory> expiredReservations = deviceReservationRepository.findByExpirationTimeBeforeAndCheckOutStatusIsNull(
//                currentTime);
//        for (ReservationHistory reservation : expiredReservations) {
//            reservation.setCheckOutStatus(CheckOutStatus.TIMER_END);
//            reservation.setCheckOutEndTime(currentTime);
//            reservationHistoryRepository.save(reservation);
//            TextFile textFile = reservation.getTextFile();
//            textFile.setReservationStatus(ReservationStatus.FREE);
//            textFileRepository.save(textFile);
//        }
//    }
}
