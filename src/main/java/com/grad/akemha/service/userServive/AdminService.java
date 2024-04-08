package com.grad.akemha.service.userServive;

import com.grad.akemha.dto.AddDoctorDto;
import com.grad.akemha.entity.DoctorSpecialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.repository.DoctorSpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorSpecializationRepository doctorSpecializationRepository;

    public List<User> getDoctors() {
        return userRepository.findAll();
    }

    public void addDoctor(AddDoctorDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        //check if the specialization not exist  create it and assign it to user
        DoctorSpecialization doctorSpecialization = doctorSpecializationRepository.findBySpecializationType(request.getSpecialization());
        if (doctorSpecialization==null) {
            doctorSpecialization.setSpecializationType(request.getSpecialization());
            doctorSpecializationRepository.save(doctorSpecialization);
        }
        user.setDoctorSpecialization(doctorSpecialization);
        userRepository.save(user);
    }

    public void deleteDoctor(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<DoctorSpecialization> getDoctorSpecialization() {
        return doctorSpecializationRepository.findAll();
    }


}
