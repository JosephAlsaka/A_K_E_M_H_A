package com.grad.akemha.service;

import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.dto.medicine.MedicineResponse;
import com.grad.akemha.entity.*;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.AlarmHistoryRepository;
import com.grad.akemha.repository.AlarmRepository;
import com.grad.akemha.repository.MedicineRepository;
import com.grad.akemha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class MedicalNotebookService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    public List<Medicine> getMedicine(HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        return medicineRepository.findByUser(user);
    }

    public List<Alarm> getAlarm(Long medicineId, HttpHeaders httpHeader) {
        User user = jwtService.extractUserFromToken(httpHeader);
        Medicine medicine = medicineRepository.findByIdAndUser(medicineId, user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        return alarmRepository.findByMedicine(medicine);
    }


    public void addMedicine(AddMedicineRequest request, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setDescription(request.getDescription());
        medicine.setUser(user);
        medicine = medicineRepository.save(medicine);
        Alarm alarm = new Alarm();
        alarm.setMedicine(medicine);
        alarm.setStartDate(request.getStartDate());
        alarm.setEndDate(request.getEndDate());
        alarm.setAlarmTime(request.getAlarmTime());
        alarmRepository.save(alarm);
    }

    public void addAlarm(AddAlarmRequest request, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = medicineRepository.findByIdAndUser(request.getMedicineId(), user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        Alarm alarm = new Alarm();
        alarm.setMedicine(medicine);
        alarm.setStartDate(request.getStartDate());
        alarm.setEndDate(request.getEndDate());
        alarm.setAlarmTime(request.getAlarmTime());
        alarmRepository.save(alarm);
    }


    public void deleteMedicine(Long medicineId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = medicineRepository.findByIdAndUser(medicineId, user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        medicineRepository.delete(medicine);
    }

    public void deleteAlarm(Long alarmId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new UserNotFoundException("Alarm not found"));
        Medicine medicine = medicineRepository.findByIdAndUser(alarm.getMedicine().getId(), user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        alarmRepository.delete(alarm);
    }

    public void takeMedicine(Long alarmId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new UserNotFoundException("Alarm not found"));
        Medicine medicine = medicineRepository.findByIdAndUser(alarm.getMedicine().getId(), user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        AlarmHistory alarmHistory=new AlarmHistory();
        alarmHistory.setAlarm(alarm);
        alarmHistory.setTakeDate(LocalDateTime.now());
        alarmHistoryRepository.save(alarmHistory);
    }


}
