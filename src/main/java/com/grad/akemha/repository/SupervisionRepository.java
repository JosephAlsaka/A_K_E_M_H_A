package com.grad.akemha.repository;

import com.grad.akemha.entity.Supervision;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.SupervisionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisionRepository extends JpaRepository<Supervision, Long> {

    List<Supervision> findBySupervisionStatusAndSupervised(SupervisionStatus status, User supervised);
    List<Supervision> findBySupervisionStatusAndSupervisor(SupervisionStatus status, User supervised);


}
