package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.beneficiary.AddBeneficiaryRequest;
import com.grad.akemha.dto.beneficiary.BeneficiaryResponse;
import com.grad.akemha.dto.beneficiary.UserRestrictionResponse;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.dto.doctor.AddDoctorRequest;
import com.grad.akemha.dto.post.PostResponse;
import com.grad.akemha.dto.user.response.UserFullResponse;
import com.grad.akemha.dto.user.response.UserLessResponse;
import com.grad.akemha.entity.Post;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

//
//    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('DOCTOR')")
//    @GetMapping()
//    public ResponseEntity<BaseResponse<List<ConsultationRes>>> getAllConsultations() { //ConsultationResponse
//        List<ConsultationRes> response = consultationService.getAllConsultations();
//        return ResponseEntity.ok()
//                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
//    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping(value = "/information/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//only beneficiary
    public ResponseEntity<BaseResponse<User>> editUserInformation(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "phoneNumber", required = false) String phoneNumber, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "dob", required = false) LocalDate dob, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg, @RequestParam(value = "gender", required = false) Gender gender, @RequestHeader HttpHeaders httpHeaders) {
        try {
            User response = userService.editUserInformation(name, phoneNumber, password, dob, profileImg, gender, httpHeaders);
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping(value = "/information/doctor/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//only beneficiary
    public ResponseEntity<BaseResponse<User>> editDoctorInformation(@RequestParam(value = "name", required = false) String name,
                                                                    @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                                    @RequestParam(value = "password", required = false) String password,
                                                                    @RequestParam(value = "dob", required = false) LocalDate dob,
                                                                    @RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
                                                                    @RequestParam(value = "gender", required = false) Gender gender,
                                                                    @RequestParam(value = "description", required = false) String description,
                                                                    @RequestParam(value = "location", required = false) String location,
                                                                    @RequestParam(value = "openingTimes", required = false) String openingTimes,
                                                                    @RequestHeader HttpHeaders httpHeaders) {
        try {
            User response = userService.editDoctorInformation(name, phoneNumber, password, dob, profileImg, gender, description, location, openingTimes, httpHeaders);
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR')")
    @GetMapping(value = "/information/{user_id}")
    public ResponseEntity<BaseResponse<UserFullResponse>> viewUserInformation(@PathVariable Long user_id) {
        try {
            UserFullResponse response = userService.viewUserInformation(user_id);
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "failed", null));
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR')")
    @GetMapping("/keyword")
    public ResponseEntity<BaseResponse<List<UserLessResponse>>> getUsersByKeyword(@RequestParam String keyword) {
        List<UserLessResponse> response = userService.findUsersByKeyword(keyword);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @GetMapping("/beneficiary")
    public ResponseEntity<BaseResponse<Page<BeneficiaryResponse>>> getBeneficiaries(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<User> beneficiariesPage = userService.getBeneficiaries(page);
        Page<BeneficiaryResponse> responsePage = beneficiariesPage.map(BeneficiaryResponse::new);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "beneficiaries", responsePage));
    }


    @PostMapping("/beneficiary")
    public ResponseEntity<BaseResponse<?>> addBeneficiary(@RequestBody AddBeneficiaryRequest request) {
        userService.addBeneficiary(request);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "beneficiary added successfully", null));
    }

    @DeleteMapping("beneficiary/{userId}")
    public ResponseEntity<BaseResponse<String>> deleteBeneficiary(@PathVariable Long userId) {
        userService.deleteBeneficiary(userId);
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "beneficiary deleted successfully", null));
    }

    @PatchMapping("beneficiary/restriction/{userId}")
    public ResponseEntity<BaseResponse<UserRestrictionResponse>> userRestriction(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new BaseResponse<>(HttpStatus.OK.value(), "successfully", userService.userRestriction(userId)));

    }
}