package com.grad.akemha.repository;

import com.grad.akemha.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    List<Specialization> findAll();
    Specialization findBySpecializationType(String specializationType);
    Optional<Specialization> findById(Long id);

    List<Specialization> findByIsPublicFalse();
}
