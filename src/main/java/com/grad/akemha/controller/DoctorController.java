package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.doctor.AddDoctorRequest;
import com.grad.akemha.dto.doctor.DoctorResponse;
import com.grad.akemha.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.grad.akemha.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('OWNER')")
    @GetMapping()
    public ResponseEntity<BaseResponse<Page<User>>> getDoctors(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<User> doctorsPage = doctorService.getDoctors(page);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctors", doctorsPage));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addDoctor(@Valid @RequestBody AddDoctorRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1); // Remove the last "; "
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), null));
        }
        doctorService.addDoctor(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor added successfully", null));
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<String>> deleteDoctor(@PathVariable Long userId) {
        doctorService.deleteDoctor(userId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor deleted successfully", null));
    }


}

