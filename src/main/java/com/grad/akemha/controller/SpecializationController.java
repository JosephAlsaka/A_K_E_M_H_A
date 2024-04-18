package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.specializationDTO.SpecializationRequest;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<Specialization>>> getSpecializations() {
        List<Specialization> specializations = specializationService.getSpecializations();
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "specializations", specializations));
    }

    @DeleteMapping("/{specializationId}") // TODO: note: all consultation will be deleted because of casecade. solve it later
    public ResponseEntity<BaseResponse<Specialization>> deleteSpecializationById(@PathVariable Long specializationId) {
        Specialization response = specializationService.deleteSpecializationById(specializationId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "specializations", response));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Specialization>> addSpecialization(@RequestBody SpecializationRequest request) {
        Specialization savedSpecialization = specializationService.addSpecialization(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "specializations", savedSpecialization));
    }
}
