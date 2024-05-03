package com.grad.akemha.controller;


import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.medical_record.MedicalRecordRequest;
import com.grad.akemha.dto.medical_record.MedicalRecordResponse;
import com.grad.akemha.entity.MedicalRecord;
import com.grad.akemha.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical_record")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    // Read
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('OWNER')")
    @GetMapping()
    public ResponseEntity<BaseResponse<MedicalRecordResponse>> getMedicalRecord(
            @RequestHeader HttpHeaders httpHeaders) {
        MedicalRecordResponse response = medicalRecordService.getLastMedicalRecord(httpHeaders);

        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Medical Record Found successfully", response));
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/history/{id}")
    public ResponseEntity<BaseResponse<List<MedicalRecordResponse>>> getAllMedicalRecord(@PathVariable Long id) {
        List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecord(id);
        List<MedicalRecordResponse> response = medicalRecordList.stream().map(MedicalRecordResponse::new).toList();
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "All MedicalRecord", response));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<BaseResponse<MedicalRecordResponse>> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequest medicalRecordRequest,
            @RequestHeader HttpHeaders httpHeaders) {
        MedicalRecordResponse response = medicalRecordService.createMedicalRecord(medicalRecordRequest, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.CREATED.value(), "Medical Record created successfully", response));
    }

}
