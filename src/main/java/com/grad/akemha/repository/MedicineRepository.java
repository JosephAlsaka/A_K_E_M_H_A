package com.grad.akemha.repository;

import com.grad.akemha.entity.Medicine;
import com.grad.akemha.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByIdAndUser (Long id, User user);
    Optional<Medicine> findByLocalIdAndUser (Long localId, User user);
    @Transactional
    void deleteByLocalIdAndUser(Long localId, User user);

    List<Medicine> findByUser(User user);



}
