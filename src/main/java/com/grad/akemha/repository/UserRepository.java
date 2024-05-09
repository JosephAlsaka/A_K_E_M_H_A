package com.grad.akemha.repository;

import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    // to delete users that are not verified
    // note this function is called in AccountCleanupTask in bootstrap folder
    @Query("SELECT u FROM User u WHERE u.isVerified = false AND u.creationDate < :timeLimit")
    List<User> findUnverifiedAccountsCreatedBefore(LocalDateTime timeLimit);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    List<User> findByKeywordInName(String keyword);
}