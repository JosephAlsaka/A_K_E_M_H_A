package com.grad.akemha.repository;

import com.grad.akemha.entity.Alarm;
import com.grad.akemha.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByMedicine(Medicine medicine);
}
