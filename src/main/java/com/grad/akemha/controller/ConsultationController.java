package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.consultation.consultationRequest.AnswerConsultationRequest;
import com.grad.akemha.dto.consultation.consultationRequest.ConsultationRequest;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.enums.ConsultationType;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private JwtService jwtService;

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping()
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAllConsultations() { //ConsultationResponse
        List<ConsultationRes> response = consultationService.getAllConsultations();
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/answered")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAllAnsweredConsultations() { //ConsultationResponse
        List<ConsultationRes> response = consultationService.getAllAnsweredConsultations();
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/{specializationId}")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getConsultationsBySpecialization(@PathVariable Long specializationId) {
        List<ConsultationRes> response = consultationService.getConsultationsBySpecializationId(specializationId);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/answered/{specializationId}")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAnsweredConsultationsBySpecializationId(@PathVariable Long specializationId) {
        List<ConsultationRes> response = consultationService.getAnsweredConsultationsBySpecializationId(specializationId);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

//    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
//    @GetMapping("/{specialization}")
//    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPendingConsultationsBySpecialization(@PathVariable String specialization) { //TODO get only Pending
//        List<ConsultationRes> response = consultationService.getConsultationsBySpecialization(specialization);
//        return ResponseEntity.ok()
//                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
//    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/consultations")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getConsultationsByKeyword(@RequestParam String keyword) {
        List<ConsultationRes> response = consultationService.findConsultationsByKeyword(keyword);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Consultation>> postConsultation(
//            @RequestPart("request") ConsultationRequest request,
//            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "consultationText", required = true) String consultationText,
            @RequestParam(value = "specializationId", required = true) Long specializationId,
            @RequestParam(value = "consultationType", required = true) ConsultationType consultationType,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestHeader HttpHeaders httpHeaders) {
                try {
            Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
            System.out.println("Converted Long value: " + beneficiaryId);
            ConsultationRequest request = new ConsultationRequest();
            request.setTitle(title);
            request.setConsultationText(consultationText);
            request.setSpecializationId(specializationId);
            request.setConsultationType(consultationType);
                    // Merge the files into the request
                    if (files != null && !files.isEmpty()) {
                        System.out.println("sami");
                        request.setFiles(files);
                    }
            Consultation response = consultationService.saveConsultation(request, beneficiaryId);
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Consultation added successfully", null));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }


    @PatchMapping("/")
    public ResponseEntity<BaseResponse<Consultation>> answerConsultation(@RequestBody AnswerConsultationRequest request, @RequestHeader HttpHeaders httpHeaders) {
        try {
            Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
            System.out.println("Converted Long value: " + doctorId);
            Consultation response = consultationService.answerConsultation(request, doctorId);
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", null));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/PersonalNullConsultations")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPersonalNullConsultations(@RequestHeader HttpHeaders httpHeaders) { //P.11
        List<ConsultationRes> response = consultationService.getPersonalNullConsultations(httpHeaders);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/PersonalAnsweredConsultations")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPersonalAnsweredConsultations(@RequestHeader HttpHeaders httpHeaders) { //P.11
        List<ConsultationRes> response = consultationService.getPersonalAnsweredConsultations(httpHeaders);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/PendingConsultationsForDoctor")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPendingConsultationsForDoctor(@RequestHeader HttpHeaders httpHeaders) { // P.12
        System.out.println("tesssst");
        List<ConsultationRes> response = consultationService.getPendingConsultationsForDoctor(httpHeaders);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }
}
