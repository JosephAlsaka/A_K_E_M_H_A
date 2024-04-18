package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.auth.doctor.AddDoctorRequest;
import com.grad.akemha.entity.User;
import com.grad.akemha.service.userService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/doctor")
public class Admin2Controller {
    @Autowired
    AdminService adminService;
    //comment test
    //comment test2222
    @GetMapping()
    public ResponseEntity<BaseResponse<List<User>>> getDoctors() {
        List<User> doctors = adminService.getDoctors();
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctors", doctors));
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addDoctor(@RequestBody AddDoctorRequest request) {
        adminService.addDoctor(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor added successfully", null));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<String>> deleteDoctor(@PathVariable Long userId) {
        adminService.deleteDoctor(userId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "doctor deleted successfully",null));
    }




}

