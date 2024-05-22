package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.medicine.AddAlarmRequest;
import com.grad.akemha.dto.medicine.AddMedicineRequest;
import com.grad.akemha.service.MedicalNotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/medicine")
public class MedicalNotebookController {

    @Autowired
    MedicalNotebookService medicalNotebookService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addMedicine(@RequestBody AddMedicineRequest request, @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.addMedicine(request, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "medicine added successfully", null));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("addTakeTime")
    public ResponseEntity<BaseResponse<?>> addAlarm(@RequestBody AddAlarmRequest request, @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.addAlarm(request, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "alarm added successfully", null));
    }

    //        @PreAuthorize("hasRole('USER')")
//    @PatchMapping() //TODO  medicine just have name???
//    public ResponseEntity<BaseResponse<?>> editMedicine() {
//
//        Consultation response = medicalNotebookService.editMedicine(request, httpHeaders);
//        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "edited successfully", null));
//    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{medicineId}")
    public ResponseEntity<BaseResponse<?>> deleteMedicine(@PathVariable Long medicineId, @RequestHeader HttpHeaders httpHeaders) {
        medicalNotebookService.deleteMedicine(medicineId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "medicine delete successfully", null));
    }


}
