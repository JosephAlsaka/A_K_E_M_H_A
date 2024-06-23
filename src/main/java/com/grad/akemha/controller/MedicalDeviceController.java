package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.medicalDevice.AddDeviceRequest;
import com.grad.akemha.dto.medicalDevice.ChangeQuantityRequest;
import com.grad.akemha.dto.medicalDevice.ReserveDeviceRequest;
import com.grad.akemha.entity.DeviceReservation;
import com.grad.akemha.entity.MedicalDevice;
import com.grad.akemha.service.MedicalDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/medical-device")
public class MedicalDeviceController {
    @Autowired
    MedicalDeviceService medicalDeviceService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<MedicalDevice>>> getDevices(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        List<MedicalDevice> devices = medicalDeviceService.getDevices(page);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "devices", devices));
    }


    @PostMapping()
    public ResponseEntity<BaseResponse<?>> addDevice(@Valid @ModelAttribute AddDeviceRequest request, BindingResult bindingResult, @RequestHeader HttpHeaders httpHeaders) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1); // Remove the last "; "
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), null));
        }
        medicalDeviceService.addDevice(request, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device added successfully", null));
    }

    @DeleteMapping("/{medicalDeviceId}")
    public ResponseEntity<BaseResponse<String>> deleteDevice(@PathVariable Long medicalDeviceId) {
        medicalDeviceService.deleteDevice(medicalDeviceId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device deleted successfully", null));
    }

    @GetMapping("/reservation/{medicalDeviceId}")
    public ResponseEntity<BaseResponse<List<DeviceReservation>>> getReservations(@PathVariable Long medicalDeviceId) {
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device reservations", medicalDeviceService.getReservations(medicalDeviceId)));
    }


    @GetMapping("/user")
    public ResponseEntity<BaseResponse<List<DeviceReservation>>> getUserReservations(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), " user device reservations", medicalDeviceService.getUserReservations(httpHeaders)));
    }

    @PostMapping("reserve")
    public ResponseEntity<BaseResponse<?>> reserveDevice(@Valid @RequestBody ReserveDeviceRequest request, BindingResult bindingResult, @RequestHeader HttpHeaders httpHeaders) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1); // Remove the last "; "
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), null));
        }
        medicalDeviceService.reserveDevice(request, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device reserved successfully", null));
    }

    @DeleteMapping("reserve/{deviceReservationId}")
    public ResponseEntity<BaseResponse<?>> deleteDeviceReservation(@PathVariable Long deviceReservationId, @RequestHeader HttpHeaders httpHeaders) {
        medicalDeviceService.deleteDeviceReservation(deviceReservationId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device reservation canceled successfully", null));
    }

    @PostMapping("delivery/{deviceReservationId}")
    public ResponseEntity<BaseResponse<String>> deviceDelivery(@PathVariable Long deviceReservationId) {
        medicalDeviceService.deviceDelivery(deviceReservationId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device delivered successfully", null));
    }

    @PostMapping("rewind/{deviceReservationId}")
    public ResponseEntity<BaseResponse<String>> deviceRewind(@PathVariable Long deviceReservationId) {
        medicalDeviceService.deviceRewind(deviceReservationId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "device rewind successfully", null));
    }

    @PatchMapping("changQuantity/{medicalDeviceId}")
    public ResponseEntity<BaseResponse<String>> changQuantity(@RequestBody ChangeQuantityRequest request, @PathVariable Long medicalDeviceId) {
        medicalDeviceService.changQuantity(medicalDeviceId, request.getQuantity());
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "quantity changed successfully", null));
    }
}
