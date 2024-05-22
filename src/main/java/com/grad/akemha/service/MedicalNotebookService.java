package com.grad.akemha.service;

import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.entity.Alarm;
import com.grad.akemha.entity.Medicine;
import com.grad.akemha.entity.User;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.AlarmRepository;
import com.grad.akemha.repository.MedicineRepository;
import com.grad.akemha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class MedicalNotebookService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    public void addMedicine(AddMedicineRequest request, HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setUser(user);
        medicine = medicineRepository.save(medicine);
        Alarm alarm = new Alarm();
        alarm.setMedicine(medicine);
        alarm.setStartDate(request.getStartDate());
        alarm.setEndDate(request.getEndDate());
        alarm.setAlarmTime(request.getAlarmTime());
        alarmRepository.save(alarm);
    }
    public void addAlarm(AddAlarmRequest request,HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine= medicineRepository.findByIdAndUser(request.getMedicineId(),user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        Alarm alarm=new Alarm();
        alarm.setMedicine(medicine);
        alarm.setStartDate(request.getStartDate());
        alarm.setEndDate(request.getEndDate());
        alarm.setAlarmTime(request.getAlarmTime());
        alarmRepository.save(alarm);
    }

    //TODO
//    public void editMedicine(AddAlarmRequest request,HttpHeaders httpHeaders) {
//    }

    public void deleteMedicine(Long medicineId,HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        Medicine medicine = medicineRepository.findByIdAndUser(medicineId,user).orElseThrow(() -> new UserNotFoundException("Medicine not found"));
        medicineRepository.delete(medicine);
    }
}
