package com.grad.akemha.repository;

import com.grad.akemha.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    List<Specialization> findAll();
    Specialization findBySpecializationType(String specializationType);
}
