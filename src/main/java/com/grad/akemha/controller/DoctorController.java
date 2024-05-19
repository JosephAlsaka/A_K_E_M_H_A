package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.doctor.AddDoctorRequest;
import com.grad.akemha.dto.doctor.DoctorResponse;
import com.grad.akemha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.grad.akemha.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @GetMapping()
    public ResponseEntity<BaseResponse<Page<User>>> getDoctors(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<User> doctorsPage = doctorService.getDoctors(page);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctors", doctorsPage));
    }



    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addDoctor(@RequestBody AddDoctorRequest request) {
        doctorService.addDoctor(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor added successfully", null));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<String>> deleteDoctor(@PathVariable Long userId) {
        doctorService.deleteDoctor(userId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor deleted successfully", null));
    }


}

