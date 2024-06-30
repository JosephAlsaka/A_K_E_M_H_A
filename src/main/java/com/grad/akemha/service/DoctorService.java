package com.grad.akemha.service;

import com.grad.akemha.dto.statistic.StatisticCountResponse;
import com.grad.akemha.dto.doctor.AddDoctorRequest;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Role;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SpecializationRepository specializationRepository;

    private final PasswordEncoder passwordEncoder;


    public Page<User> getDoctors(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return userRepository.findByRole(Role.DOCTOR, pageable);
    }
//    public DoctorResponse getDoctors(Integer page) {
//        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
//        Page<User> doctorPage = userRepository.findByRole(Role.DOCTOR, pageable);
//        return new DoctorResponse(doctorPage.getContent(), doctorPage.getTotalElements());
//    }

    public void addDoctor(AddDoctorRequest request) {
        if (userAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.DOCTOR);
        user.setIsVerified(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setCreationDate(LocalDateTime.now());
        Specialization specialization = specializationRepository.findBySpecializationType(request.getSpecialization());
        user.setSpecialization(specialization);
        userRepository.save(user);
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteDoctor(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
    public List<StatisticCountResponse> getDoctorCountByMonth() {
        return userRepository.countUserByMonth(Role.DOCTOR);
    }

}


