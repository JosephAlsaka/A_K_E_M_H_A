package com.grad.akemha.repository;

import com.grad.akemha.entity.Alarm;
import com.grad.akemha.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByMedicine(Medicine medicine, Pageable pageable);
}
