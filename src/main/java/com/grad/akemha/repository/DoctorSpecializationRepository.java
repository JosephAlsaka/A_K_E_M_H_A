package com.grad.akemha.repository;

import com.grad.akemha.entity.DoctorSpecialization;
import com.grad.akemha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long> {
    List<DoctorSpecialization> findAll();
    DoctorSpecialization findBySpecializationType(String specializationType);
}
