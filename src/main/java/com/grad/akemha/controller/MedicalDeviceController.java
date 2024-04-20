package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.doctor.AddDoctorRequest;
import com.grad.akemha.dto.medicalDevice.AddDeviceRequest;
import com.grad.akemha.entity.DeviceReservation;
import com.grad.akemha.entity.MedicalDevice;
import com.grad.akemha.service.MedicalDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/medical-device")
public class MedicalDeviceController {
    @Autowired
    MedicalDeviceService medicalDeviceService;


    @GetMapping()
    public ResponseEntity<BaseResponse<List<MedicalDevice>>> getDevices() {
        List<MedicalDevice> devices = medicalDeviceService.getDevices();
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "devices", devices));
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<DeviceReservation>>> getReservations(@PathVariable Long medicalDeviceId) {
        //TODO
        medicalDeviceService.getReservations(medicalDeviceId);
        return null;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addDevice(@RequestBody AddDeviceRequest request) {
        medicalDeviceService.addDevice(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device added successfully", null));
    }
}
