package com.grad.akemha.service;

import com.grad.akemha.dto.specializationDTO.SpecializationRequest;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;

    public List<Specialization> getSpecializations() {
        return specializationRepository.findAll();
    }

    public Specialization deleteSpecializationById(Long specializationId) {
        Optional<Specialization> optionalSpecialization = specializationRepository.findById(specializationId);
        if (optionalSpecialization.isPresent()) {
            Specialization specialization = optionalSpecialization.get();
            specializationRepository.deleteById(specializationId);
            return specialization;
        } else {
//            throw new NotFoundException("specialization id " + id + " is not found") //TODO
            throw new UserNotFoundException("specialization id " + specializationId + " is not found");
        }
    }

    public Specialization addSpecialization(SpecializationRequest request) {
        Specialization specialization = new Specialization();
        specialization.setIsPublic(request.isPublic());
        specialization.setSpecializationType(request.specializationType());
        return specializationRepository.save(specialization);
    }
}
