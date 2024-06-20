package com.grad.akemha.repository;

import com.grad.akemha.entity.Medicine;
import com.grad.akemha.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByIdAndUser (Long id, User user);

    Page<Medicine> findByUser(User user, Pageable pageable);

}
