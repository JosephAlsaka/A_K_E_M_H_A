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


    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping()
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAllConsultations(
            @RequestParam(name = "page", defaultValue = "0") Integer page) { //ConsultationResponse
        List<ConsultationRes> response = consultationService.getAllConsultations(page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/answered")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAllAnsweredConsultations(
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) { //ConsultationResponse
        List<ConsultationRes> response = consultationService.getAllAnsweredConsultations(page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/{specializationId}")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getConsultationsBySpecialization(
            @PathVariable Long specializationId,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        List<ConsultationRes> response = consultationService.getConsultationsBySpecializationId(specializationId, page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/answered/{specializationId}")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAnsweredConsultationsBySpecializationId(
            @PathVariable Long specializationId,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        List<ConsultationRes> response = consultationService.getAnsweredConsultationsBySpecializationId(specializationId, page);
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
    @GetMapping("/keyword")
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
            Consultation response = consultationService.postConsultation(httpHeaders, title, consultationText, specializationId, consultationType, files);
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Consultation added successfully", response));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }


    @PatchMapping("/")
    public ResponseEntity<BaseResponse<Consultation>> answerConsultation(@RequestBody AnswerConsultationRequest request, @RequestHeader HttpHeaders httpHeaders) {
        try {
            Consultation response = consultationService.answerConsultation(request, httpHeaders);
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
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPersonalAnsweredConsultations(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "page", defaultValue = "0") Integer page) { //P.11
        List<ConsultationRes> response = consultationService.getPersonalAnsweredConsultations(httpHeaders, page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/PendingConsultationsForDoctor")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getPendingConsultationsForDoctor(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "page", defaultValue = "0") Integer page) { // P.12
        List<ConsultationRes> response = consultationService.getPendingConsultationsForDoctor(httpHeaders, page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/getBeneficiaryAnsweredConsultations/{beneficiaryId}")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getBeneficiaryAnsweredConsultations(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable Long beneficiaryId,
            @RequestParam(name = "page", defaultValue = "0") Integer page) { //P.13
        List<ConsultationRes> response = consultationService.getBeneficiaryAnsweredConsultations(beneficiaryId, page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
    @GetMapping("/getDoctorAnsweredConsultations")
    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getDoctorAnsweredConsultations(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "page", defaultValue = "0") Integer page) { //P.14
        List<ConsultationRes> response = consultationService.getDoctorAnsweredConsultations(httpHeaders, page);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }
}
