package com.grad.akemha.service.userServive;

import com.grad.akemha.dto.AddDoctorDto;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SpecializationRepository specializationRepository;

    private final PasswordEncoder passwordEncoder;


    public List<User> getDoctors() {
        return userRepository.findAll();
    }

    public User addDoctor(AddDoctorDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        //check if the specialization not exist  create it and assign it to user
        Specialization specialization = specializationRepository.findBySpecializationType(request.getSpecialization());
        System.out.println(specialization);
        if (specialization == null) {
            specialization=new Specialization();
            specialization.setSpecializationType(request.getSpecialization());
            specializationRepository.save(specialization);
        }
        user.setSpecialization(specialization);
        return userRepository.save(user);
    }

    public boolean deleteDoctor(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<Specialization> getDoctorSpecialization() {
        return specializationRepository.findAll();
    }


}


