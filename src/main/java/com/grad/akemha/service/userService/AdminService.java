package com.grad.akemha.service.userService;

import com.grad.akemha.dto.auth.doctor.AddDoctorRequest;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Role;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SpecializationRepository specializationRepository;

    private final PasswordEncoder passwordEncoder;


    public List<User> getDoctors() {
        return userRepository.findByRole(Role.DOCTOR);
    }

    public void addDoctor(AddDoctorRequest request) {
        if (userAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.DOCTOR);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        //check if the specialization not exist  create it and assign it to user
        Specialization specialization = specializationRepository.findBySpecializationType(request.getSpecialization());
//        System.out.println(specialization);
        if (specialization == null) {
            specialization = new Specialization();
            specialization.setSpecializationType(request.getSpecialization());
            specializationRepository.save(specialization);
        }
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


}


