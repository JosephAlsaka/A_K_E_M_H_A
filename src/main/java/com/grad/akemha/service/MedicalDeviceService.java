package com.grad.akemha.service;

import com.grad.akemha.dto.medicalDevice.AddDeviceRequest;
import com.grad.akemha.dto.medicalDevice.ReserveDeviceRequest;
import com.grad.akemha.entity.DeviceReservation;
import com.grad.akemha.entity.MedicalDevice;
import com.grad.akemha.entity.User;
import com.grad.akemha.exception.DeviceAlreadyExistsException;
import com.grad.akemha.exception.DeviceNotFoundException;
import com.grad.akemha.exception.DeviceReservationNoQuantityException;
import com.grad.akemha.exception.DeviceReservationQuantityException;
import com.grad.akemha.repository.DeviceReservationRepository;
import com.grad.akemha.repository.MedicalDeviceRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalDeviceService {
    @Autowired
    private MedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private DeviceReservationRepository deviceReservationRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private JwtService jwtService;

    public List<MedicalDevice> getDevices() {
        return medicalDeviceRepository.findAll();
    }

    public List<DeviceReservation> getReservations(Long medicalDeviceId) {
        MedicalDevice medicalDevice =medicalDeviceRepository.findById(medicalDeviceId).orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        return medicalDevice.getDeviceReservations();
    }

    public void addDevice(AddDeviceRequest request, HttpHeaders httpHeaders) {
        if (deviceAlreadyExists(request.getName())) {
            throw new DeviceAlreadyExistsException("Device already exists");
        }
        User user = jwtService.extractUserFromToken(httpHeaders);
        MedicalDevice medicalDevice = new MedicalDevice();
        medicalDevice.setName(request.getName());
        medicalDevice.setCount(request.getCount());
        medicalDevice.setReservedCount(0);
        medicalDevice.setImageUrl(cloudinaryService.uploadFile(request.getImage(), "Devices", user.getId().toString()));
        medicalDeviceRepository.save(medicalDevice);
    }

    public boolean deviceAlreadyExists(String name) {
        return medicalDeviceRepository.existsByName(name);
    }

    public void reserveDevice(ReserveDeviceRequest request, HttpHeaders httpHeaders) {
        //TODO  add Constraint  to not reserve more than one time (when the status is taken or pending)
        //TODO remove the reservation automatically
        MedicalDevice medicalDevice =medicalDeviceRepository.findById(request.getMedicalDeviceId()).orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        if(request.getQuantity()>2)
            throw new DeviceReservationQuantityException("You cannot reserve more than two of the same device");
        int availableQuantity = medicalDevice.getCount() - medicalDevice.getReservedCount();
        if (request.getQuantity() <= availableQuantity) {
            int newReservedCount = medicalDevice.getReservedCount() + request.getQuantity();
            medicalDevice.setReservedCount(newReservedCount);
            User user = jwtService.extractUserFromToken(httpHeaders);
            DeviceReservation reservation = DeviceReservation.builder()
                    .user(user)
                    .medicalDevice(medicalDevice)
                    .build();
            deviceReservationRepository.save(reservation);
            medicalDeviceRepository.save(medicalDevice);
        } else {
            throw new DeviceReservationNoQuantityException("No enough devices available for reservation");
        }
    }

    public void deleteDevice(Long medicalDeviceId) {
        MedicalDevice medicalDevice = medicalDeviceRepository.findById(medicalDeviceId).orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        medicalDeviceRepository.delete(medicalDevice);
    }
}
