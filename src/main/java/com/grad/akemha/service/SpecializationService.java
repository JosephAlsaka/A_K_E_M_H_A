package com.grad.akemha.service;

import com.grad.akemha.dto.specializationDTO.SpecializationRequest;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;

    public List<Specialization> getSpecializations() {
        return specializationRepository.findAll();
    }

    public void deleteSpecializationById(Long specializationId) {
        specializationRepository.deleteById(specializationId);
        return;
    }

    public Specialization addSpecialization(SpecializationRequest request) {
        Specialization specialization = Specialization.builder()
                .specializationType(request.specializationType())
                .build();
        return specializationRepository.save(specialization);
    }
}
