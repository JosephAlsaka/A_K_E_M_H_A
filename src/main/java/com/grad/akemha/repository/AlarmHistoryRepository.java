package com.grad.akemha.repository;

import com.grad.akemha.entity.Alarm;
import com.grad.akemha.entity.AlarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory,Long> {
}
