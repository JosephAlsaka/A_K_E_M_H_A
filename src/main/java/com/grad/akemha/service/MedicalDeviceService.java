package com.grad.akemha.service;

import com.grad.akemha.dto.medicalDevice.AddDeviceRequest;
import com.grad.akemha.entity.MedicalDevice;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Role;
import com.grad.akemha.exception.DeviceAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.repository.MedicalDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalDeviceService {
    @Autowired
    private MedicalDeviceRepository medicalDeviceRepository;

    public List<MedicalDevice> getDevices() {
        return medicalDeviceRepository.findAll();
    }

    public List<MedicalDevice> getReservations(Long medicalDeviceId) {
        //TODO
        return medicalDeviceRepository.findAll();
    }

    public void addDevice(AddDeviceRequest request) {
        if (deviceAlreadyExists(request.getName())) {
            throw new DeviceAlreadyExistsException("Device already exists");
        }
        MedicalDevice medicalDevice = new MedicalDevice();
        medicalDevice.setName(request.getName());
        medicalDevice.setCount(request.getCount());
        medicalDeviceRepository.save(medicalDevice);
    }

    public boolean deviceAlreadyExists(String name) {
        return medicalDeviceRepository.existByName(name);
    }
}
