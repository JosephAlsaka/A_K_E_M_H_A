package com.grad.akemha.service;

import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.exception.CloudinaryException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CloudinaryService cloudinaryService;

    public User editUserInformation(String name, String phoneNumber, String password, LocalDate dob, MultipartFile profileImg, Gender gender, HttpHeaders httpHeaders) {
        Long userId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user Id: " + userId + " is not found"));

        User userAfterEditing = editBasicPersonalInformation(name, phoneNumber, password, dob, profileImg, gender, userId, user);
        User userResponse = userRepository.save(userAfterEditing);
        return userResponse;
    }

    private User editBasicPersonalInformation(String name, String phoneNumber, String password, LocalDate dob, MultipartFile profileImg, Gender gender, Long userId, User user) {
        if (name != null) {
            System.out.println("1");
            user.setName(name);
        }
        if (phoneNumber != null) {
            System.out.println("2");
            user.setPhoneNumber(phoneNumber);
        }
        if (password != null) {
            System.out.println("3");
            //check if password == confirmPassword
            //encypt password
            //save password // TODO
//            user.setPassword(request.password());
        }
        if (dob != null) {
            System.out.println("4");
            user.setDob(dob);
        }
        if (profileImg != null) {
            System.out.println("4");
            String profileImage = cloudinaryService.uploadFile(profileImg, "profile pictures", userId.toString());
            if (profileImage == null) {
                throw new CloudinaryException("Image upload failed");
            }
            user.setProfileImage(profileImage);
        }
        if (gender != null) {
            System.out.println("5");
            user.setGender(gender);
        }
        return user;
    }

    public User editDoctorInformation(String name, String phoneNumber, String password, LocalDate dob, MultipartFile profileImg, Gender gender, String description, String location, String openingTimes, HttpHeaders httpHeaders) {
        Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new NotFoundException("user Id: " + doctorId + " is not found"));
        User doctorAfterEditing = editBasicPersonalInformation(name, phoneNumber, password, dob, profileImg, gender, doctorId, doctor);
        if (description != null){
            doctorAfterEditing.setDescription(description);
        }
        if (location != null){
            doctorAfterEditing.setLocation(location);
        }
        if (openingTimes != null){
            doctorAfterEditing.setOpeningTimes(openingTimes);
        }
        User userResponse = userRepository.save(doctorAfterEditing);
        return userResponse;
    }

    public User viewUserInformation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user Id: " + userId + " is not found"));
        return user; //TODO, need to be edited there are to much info in response
    }
}
