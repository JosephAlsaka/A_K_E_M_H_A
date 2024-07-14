package com.grad.akemha.repository;

import com.grad.akemha.entity.DoctorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRequestRepository extends JpaRepository<DoctorRequest, Long> {
}
