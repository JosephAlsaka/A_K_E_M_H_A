package com.grad.akemha.service;

import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.dto.medicine.TakeMedicineRequest;
import com.grad.akemha.entity.AlarmHistory;
import com.grad.akemha.entity.AlarmTime;
import com.grad.akemha.entity.Medicine;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.AlarmRoutine;
import com.grad.akemha.entity.enums.AlarmRoutineType;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.AlarmHistoryRepository;
import com.grad.akemha.repository.MedicineRepository;
import com.grad.akemha.repository.SupervisionRepository;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicineNotebookService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private SupervisionRepository supervisionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    public List<Medicine> getMedicine(HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        return medicineRepository.findByUser(user);
    }

//    public List<Alarm> getAlarm(Long medicineId, HttpHeaders httpHeader) {
//        User user = jwtService.extractUserFromToken(httpHeader);
//        Medicine medicine = medicineRepository.findByIdAndUser(medicineId, user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
//        return alarmRepository.findByMedicine(medicine);
//    }


    public void addMedicine(AddMedicineRequest request, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = new Medicine();
        medicine.setName(request.getMedicamentName());
        medicine.setDescription("request.getMedicamentName()");
        medicine.setUser(user);
        medicine.setLocalId(request.getId());
        medicine.setStartDate(request.getStartDate());
        medicine.setEndDate(request.getEndDate());
        medicine.setAlarmTimes(request.getAlarmTimes());
        if (request.getAlarmRoutine().equalsIgnoreCase(AlarmRoutine.DAILY.toString())) {
            if (request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())) {
                medicine.setAlarmRoutine(AlarmRoutine.DAILY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Acute);
            } else { // Chronic
                medicine.setAlarmRoutine(AlarmRoutine.DAILY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Chronic);
            }
        }
        if (request.getAlarmRoutine().equalsIgnoreCase(AlarmRoutine.WEEKLY.toString())) {
            if (request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())) {
                medicine.setAlarmRoutine(AlarmRoutine.WEEKLY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Acute);
                medicine.setAlarmWeekDay(request.getAlarmWeekDay());
            } else { // Chronic
                medicine.setAlarmRoutine(AlarmRoutine.WEEKLY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Chronic);
                medicine.setAlarmWeekDay(request.getAlarmWeekDay());
            }
        }
        if (request.getAlarmRoutine().equalsIgnoreCase(AlarmRoutine.MONTHLY.toString())) {
            if (request.getAlarmRoutineType().equalsIgnoreCase(AlarmRoutineType.Acute.toString())) {
                medicine.setAlarmRoutine(AlarmRoutine.MONTHLY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Acute);
                medicine.setSelectedDayInMonth(request.getSelectedDayInMonth());
            } else { // Chronic
                medicine.setAlarmRoutine(AlarmRoutine.MONTHLY);
                medicine.setAlarmRoutineType(AlarmRoutineType.Chronic);
                medicine.setSelectedDayInMonth(request.getSelectedDayInMonth());
            }
        }
        medicineRepository.save(medicine);
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

//    public void deleteAlarm(Long alarmId, HttpHeaders httpHeaders) {
//        User user = jwtService.extractUserFromToken(httpHeaders);
//        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new UserNotFoundException("Alarm not found"));
//        Medicine medicine = medicineRepository.findByIdAndUser(alarm.getMedicine().getId(), user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
//        alarmRepository.delete(alarm);
//    }

    public void takeMedicine(Long localAlarmId, TakeMedicineRequest request, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = medicineRepository.findByLocalIdAndUser(localAlarmId, user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        List<AlarmTime> alarmTimes = medicine.getAlarmTimes();

        boolean alarmMatched = false;
        for (AlarmTime alarmTime : alarmTimes) {
            if (alarmTime.getTime().equals(request.getRingingTime())) {
                alarmMatched = true;
                break;
            }
        }
        if (alarmMatched) {
            AlarmHistory alarmHistory = new AlarmHistory();
            alarmHistory.setTakeTime(request.getTakeTime());
            alarmHistoryRepository.save(alarmHistory);
        }

    }

    public void getSupervisedMedicineState(Long supervisedId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        User supervised = userRepository.findById(supervisedId).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
//        if(supervisionRepository.existBySupervisedAndSupervisor(user,supervised))
//        {
        //TODO
        LocalDate currentDate = LocalDate.now();
        List<Medicine> supervisedMedicineList = medicineRepository.findCurrentMedicinesByUser(supervised, currentDate);

        //if date.now > endDate  skip.
        //list, + takeTime
//            new respone have the medicine + take time

//        }
//    else {
//
//        }
    }


    public List<Medicine> getSupervisedTodayMedicineState(Long supervisedId, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        User supervised = userRepository.findById(supervisedId).orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDate today = LocalDate.now();
        DayOfWeek todayDayOfWeek = today.getDayOfWeek();
        int todayDayOfMonth = today.getDayOfMonth();

        List<Medicine> allMedicines = medicineRepository.findCurrentMedicinesByUser(supervised, today);

        List<Medicine> printList = allMedicines.stream()
                .filter(medicine -> {
                    switch (medicine.getAlarmRoutine()) {
                        case DAILY:
                            return true;
                        case WEEKLY:
                            return medicine.getAlarmWeekDay() != null &&
                                    medicine.getAlarmWeekDay().equalsIgnoreCase(todayDayOfWeek.name());
                        case MONTHLY:
                            return medicine.getSelectedDayInMonth() != null &&
                                    medicine.getSelectedDayInMonth() == todayDayOfMonth;
                        default:
                            return false;
                    }
                })
                .peek(medicine -> {
                    List<AlarmTime> alarmTimes = medicine.getAlarmTimes();
                    for (AlarmTime alarmTime : alarmTimes) {
                        boolean isTaken = alarmHistoryRepository.existsByAlarmTimeAndTakeTimeBetween(
                                alarmTime,
                                alarmTime.getTime().atDate(today).toLocalDate().atStartOfDay(),
                                alarmTime.getTime().atDate(today).plusDays(1).toLocalDate().atStartOfDay()
                        );
                        alarmTime.setTaken(isTaken);
                    }
                })
                .collect(Collectors.toList());

        System.out.println(printList);

        return printList;
    }

}
