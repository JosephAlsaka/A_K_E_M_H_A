package com.grad.akemha.Controller;

import com.grad.akemha.dto.AddDoctorDto;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.service.userServive.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/admin/doctor")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping()
    public ResponseEntity<List<User>> getDoctors() {
        List<User> doctors = adminService.getDoctors();
        return ResponseEntity.ok(doctors);
    }
    @PostMapping()
    public ResponseEntity<?> addDoctor(@RequestBody AddDoctorDto request) {
        //TODO add role (doctor) to user
        try {
            if (adminService.addDoctor(request) != null) {
                return ResponseEntity.ok().body("doctor added successfully");
            }

        } catch (Exception e) {
            String errorMessage = "Error occurred while adding the doctor: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        return null;
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long userId) {
        boolean isDeleted = adminService.deleteDoctor(userId);
        if (isDeleted) {
            return ResponseEntity.ok().body("Doctor deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }

    @GetMapping("/specialization")
    public List<Specialization> getDoctorSpecialization() {
        return adminService.getDoctorSpecialization();
    }

}

