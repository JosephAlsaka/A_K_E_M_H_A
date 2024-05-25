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

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/medicine")
public class MedicalNotebookController {

    @Autowired
    MedicalNotebookService medicalNotebookService;

    @GetMapping()
    public ResponseEntity<BaseResponse<Page<MedicineResponse>>> getMedicine(@RequestParam(name = "page", defaultValue = "0") int page, @RequestHeader HttpHeaders httpHeaders) {
        Page<Medicine> medicinePage = medicalNotebookService.getMedicine(httpHeaders, page);
        Page<MedicineResponse> responsePage = medicinePage.map(MedicineResponse::new);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "medicines", responsePage));
    }

    @GetMapping("/alarm/{medicineId}")
    public ResponseEntity<BaseResponse<Page<Alarm>>> getAlarm(@PathVariable Long medicineId, @RequestParam(name = "page", defaultValue = "0") int page, @RequestHeader HttpHeaders httpHeaders) {
        Page<Alarm> medicinePage = medicalNotebookService.getAlarm(medicineId, httpHeaders, page);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "alarms", medicinePage));
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Void>> addMedicine(
            @Valid @RequestBody AddMedicineRequest request,
            BindingResult bindingResult,
            @RequestHeader HttpHeaders httpHeaders) {

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
                                                    BindingResult bindingResult, @RequestHeader HttpHeaders httpHeaders) {
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
