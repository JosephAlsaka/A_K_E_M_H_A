package com.grad.akemha.Controller;

import com.grad.akemha.dto.AddDoctorDto;
import com.grad.akemha.entity.User;
import com.grad.akemha.service.userServive.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController("/admin/doctor")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping()
    public ResponseEntity<List<User>> getDoctors(@RequestBody List<Long> fileIds) {
        return (ResponseEntity<List<User>>) adminService.getDoctors();
    }

    @PostMapping()
    public ResponseEntity<String> addDoctor(@RequestBody AddDoctorDto request) {
        //TODO add role (doctor) to user
        try {
            adminService.addDoctor(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteDoctor(@PathVariable  Long userId) {
        try {
            adminService.deleteDoctor(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

