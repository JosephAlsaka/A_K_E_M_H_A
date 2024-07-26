package com.grad.akemha.service;

import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.entity.*;
import com.grad.akemha.entity.enums.AlarmRoutine;
import com.grad.akemha.entity.enums.AlarmRoutineType;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.AlarmHistoryRepository;
import com.grad.akemha.repository.AlarmRepository;
import com.grad.akemha.repository.MedicineRepository;
import com.grad.akemha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class MedicineNotebookService {
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
        Medicine medicine = Medicine.builder()
                .name(request.getMedicamentName())
                .description("description")
                .user(user)
                .localId(request.getId())
                .build();
        medicineRepository.save(medicine);

        if(request.getAlarmRoutine().equalsIgnoreCase(AlarmRoutine.Daily.toString())){
            if(request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())){
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Daily)
                        .alarmRoutineType(AlarmRoutineType.Acute)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .medicine(medicine)
                        .build();
                alarmRepository.save(alarm);
            }else{ // Chronic
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Daily)
                        .alarmRoutineType(AlarmRoutineType.Chronic)
                        .startDate(request.getStartDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .medicine(medicine)
                        .build();
                alarmRepository.save(alarm);
            }
        }
        if(request.getAlarmRoutine().equals(AlarmRoutine.Weekly)){
            if(request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())){
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Weekly)
                        .alarmRoutineType(AlarmRoutineType.Acute)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .medicine(medicine)
                        .alarmWeekDay(request.getAlarmWeekDay())
                        .build();
                alarmRepository.save(alarm);
            }else{ // Chronic
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Weekly)
                        .alarmRoutineType(AlarmRoutineType.Chronic)
                        .startDate(request.getStartDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .medicine(medicine)
                        .alarmWeekDay(request.getAlarmWeekDay())
                        .build();
                alarmRepository.save(alarm);
            }
        }
        if(request.getAlarmRoutine().equals(AlarmRoutine.Monthly)){
            if(request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())){
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Weekly)
                        .alarmRoutineType(AlarmRoutineType.Acute)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .selectedDayInMonth(request.getSelectedDayInMonth())
                        .medicine(medicine)
                        .build();
                alarmRepository.save(alarm);
            }else{ // Chronic
                Alarm alarm = Alarm.builder()
                        .alarmRoutine(AlarmRoutine.Weekly)
                        .alarmRoutineType(AlarmRoutineType.Chronic)
                        .startDate(request.getStartDate())
                        .alarmTime(request.getAlarmTimes().get(0))
                        .selectedDayInMonth(request.getSelectedDayInMonth())
                        .medicine(medicine)
                        .build();
                alarmRepository.save(alarm);
            }
        }

    }

    public void addAlarm(AddAlarmRequest request, HttpHeaders httpHeaders) {
//        User user = jwtService.extractUserFromToken(httpHeaders);
//        Medicine medicine = medicineRepository.findByIdAndUser(request.getMedicineId(), user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
//        Alarm alarm = new Alarm();
//        alarm.setMedicine(medicine);
//        alarm.setStartDate(request.getStartDate());
//        alarm.setEndDate(request.getEndDate());
//        alarm.setAlarmTime(request.getAlarmTime());
//        alarmRepository.save(alarm);
    }


    public void deleteMedicine(Long medicineId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        medicineRepository.deleteByLocalIdAndUser(medicineId, user);
//                .orElseThrow(() -> new UserNotFoundException("Medicine not found"));
//        medicineRepository.delete(medicine);
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
