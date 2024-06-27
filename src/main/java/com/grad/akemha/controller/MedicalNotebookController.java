package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.dto.medicine.MedicineResponse;
import com.grad.akemha.entity.Alarm;
import com.grad.akemha.entity.Medicine;
import com.grad.akemha.service.MedicalNotebookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/medicine")
public class MedicalNotebookController {

    @Autowired
    MedicalNotebookService medicalNotebookService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<MedicineResponse>>> getMedicine(@RequestHeader HttpHeaders httpHeaders) {
        List<Medicine> medicines = medicalNotebookService.getMedicine(httpHeaders);
        List<MedicineResponse> responsePage = medicines.stream().map(MedicineResponse::new).toList();

        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "medicines", responsePage));
    }

    @GetMapping("/alarm/{medicineId}")
    public ResponseEntity<BaseResponse<List<Alarm>>> getAlarm(@PathVariable Long medicineId,@RequestHeader HttpHeaders httpHeaders) {
        List<Alarm> alarms = medicalNotebookService.getAlarm(medicineId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "alarms", alarms));
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Void>> addMedicine(
            @Valid @RequestBody AddMedicineRequest request,
            @RequestHeader HttpHeaders httpHeaders,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1); // Remove the last "; "
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), null));
        }
        medicalNotebookService.addMedicine(request, httpHeaders);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "Medicine added successfully", null));
    }

    @PostMapping("alarm")
    public ResponseEntity<BaseResponse<?>> addAlarm(@Valid @RequestBody AddAlarmRequest request,
                                                    @RequestHeader HttpHeaders httpHeaders,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1);
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), null));
        }
        medicalNotebookService.addAlarm(request, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "alarm added successfully", null));
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<BaseResponse<?>> deleteMedicine(@PathVariable Long medicineId, @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.deleteMedicine(medicineId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "medicine delete successfully", null));
    }

    @DeleteMapping("alarm/{alarmId}")
    public ResponseEntity<BaseResponse<?>> deleteAlarm(@PathVariable Long alarmId, @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.deleteAlarm(alarmId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "alarm delete successfully", null));
    }

    @PostMapping("/take/{AlarmId}")
    public ResponseEntity<BaseResponse<?>> takeMedicine(@PathVariable Long AlarmId,
                                                        @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.takeMedicine(AlarmId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", null));
    }
}
