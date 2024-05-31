package com.grad.akemha.repository;

import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    Page<User> findByRole(Role role, Pageable pageable);

    List<User> findAllByRole(Role role);
    // to delete users that are not verified
    // note this function is called in AccountCleanupTask in bootstrap folder
    @Query("SELECT u FROM User u WHERE u.isVerified = false AND u.creationDate < :timeLimit")
    List<User> findUnverifiedAccountsCreatedBefore(LocalDateTime timeLimit);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    List<User> findByKeywordInName(String keyword);

    // super vision related

    // 1. retrieve 10 user who matches they key word the most
    @Query("SELECT u FROM User u WHERE u.role <> 'OWNER' AND u.id <> :userId AND u.email LIKE %:keyword% ORDER BY CASE " +
            "WHEN u.name LIKE :keyword THEN 1 " +
            "WHEN u.name LIKE :keyword% THEN 2 " +
            "WHEN u.name LIKE %:keyword THEN 3 " +
            "ELSE 4 END, u.name ASC")
    List<User> findByNameContaining(@Param("keyword") String keyword,@Param("userId") Long userId,Pageable pageable);

    // 2. retrieve 10 random users
    @Query(value = "SELECT * FROM User WHERE role <> 'OWNER' AND id <> :userId ORDER BY RAND() LIMIT 10",nativeQuery = true)
    List<User> findRandomUsers(@Param("userId") Long userId);

}