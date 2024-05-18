package com.grad.akemha.service;

import com.grad.akemha.dto.beneficiary.AddBeneficiaryRequest;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.dto.user.response.UserFullResponse;
import com.grad.akemha.dto.user.response.UserLessResponse;
import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.entity.enums.Role;
import com.grad.akemha.exception.CloudinaryException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;


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
            //save password
            // TODO
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
        if (description != null) {
            doctorAfterEditing.setDescription(description);
        }
        if (location != null) {
            doctorAfterEditing.setLocation(location);
        }
        if (openingTimes != null) {
            doctorAfterEditing.setOpeningTimes(openingTimes);
        }
        User userResponse = userRepository.save(doctorAfterEditing);
        return userResponse;
    }

    public UserFullResponse viewUserInformation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user Id: " + userId + " is not found"));
        UserFullResponse userFullResponse = new UserFullResponse(user);
        return userFullResponse;
    }

    public List<UserLessResponse> findUsersByKeyword(String keyword) {
        List<User> userList = userRepository.findByKeywordInName(keyword);
        List<UserLessResponse> userResponseList = userList.stream().map(user -> new UserLessResponse(user)).toList();
        return userResponseList;
    }

    public List<User> getBeneficiaries(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<User> doctorPage = (Page<User>) userRepository.findByRole(Role.USER,pageable);
        return doctorPage.getContent();
    }

    public void addBeneficiary(AddBeneficiaryRequest request) {
        if (userAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setIsVerified(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        userRepository.save(user);
    }
    public void deleteBeneficiary(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
    private boolean userAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
