package com.grad.akemha.service;

import com.grad.akemha.dto.consultation.consultationRequest.AnswerConsultationRequest;
import com.grad.akemha.dto.consultation.consultationRequest.ConsultationRequest;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Image;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.ConsultationRepository;
import com.grad.akemha.repository.ImageRepository;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private JwtService jwtService;

    public List<ConsultationRes> getAllConsultations() {
        List<Consultation> consultationList = consultationRepository.findAll();
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getConsultationsBySpecializationId(Long specializationId) {
        if (specializationRepository.findById(specializationId).isPresent()) {
            List<Consultation> consultationList = consultationRepository.findBySpecializationId(specializationId);
            List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
            return consultationResponseList;
        } else {
            throw new UserNotFoundException("SpecializationId " + specializationId + " is not found"); //TODO NotFound
        }

    }

    public List<ConsultationRes> getAnsweredConsultationsBySpecializationId(Long specializationId) {
//        Optional<Specialization> specialization = specializationRepository.findById(specializationId);
        if (specializationRepository.findById(specializationId).isPresent()) {
            List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNullAndSpecializationId(specializationId);
            List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
            return consultationResponseList;
        } else {
            throw new UserNotFoundException("SpecializationId " + specializationId + " is not found"); //TODO NotFound
        }
    }

    public List<ConsultationRes> findConsultationsByKeyword(String keyword) {
        List<Consultation> consultationList = consultationRepository.findByKeywordInConsultationText(keyword);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public Consultation saveConsultation(ConsultationRequest request, Long beneficiaryId) {
        User beneficiary = userRepository.findById(beneficiaryId).orElseThrow();
        Specialization specialization = specializationRepository.findById(request.getSpecializationId()).orElseThrow();

        // Upload and save multiple images
        List<Image> images = new ArrayList<>();
        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            images = request.getFiles().stream().map(file -> {
                Image image = new Image();
                image.setImageUrl(cloudinaryService.uploadFile(file, "Consultations", beneficiaryId.toString()));
                if (image.getImageUrl() == null) {
                    throw new UserNotFoundException("Image upload failed"); //TODO change the Exception
                }
                return imageRepository.save(image);
            }).collect(Collectors.toList());
        }

        Consultation consultation = Consultation.builder().title(request.getTitle()).consultationText(request.getConsultationText()).specialization(specialization).consultationStatus(ConsultationStatus.NULL).beneficiary(beneficiary).consultationType(request.getConsultationType()).images(images).createTime(new Date()).build();
        consultationRepository.save(consultation);
        return consultation;
    }

    public Consultation answerConsultation(AnswerConsultationRequest request, Long doctorId) {
        User doctor = userRepository.findById(doctorId).orElseThrow();
        Consultation consultation = consultationRepository.findById(request.consultationId()).orElseThrow();
        consultation.setDoctor(doctor);
        consultation.setConsultationAnswer(request.answer());
        consultation.setConsultationStatus(ConsultationStatus.ARCHIVED);
        consultationRepository.save(consultation);
        return consultation;
    }

    public List<ConsultationRes> getAllAnsweredConsultations() {
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNull();
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPersonalNullConsultations(HttpHeaders httpHeaders) {
        Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        List<Consultation> consultationList = consultationRepository.findByBeneficiaryIdAndConsultationStatus(beneficiaryId, ConsultationStatus.NULL);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPersonalAnsweredConsultations(HttpHeaders httpHeaders) {
        Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNullAndBeneficiaryId(beneficiaryId);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPendingConsultationsForDoctor(HttpHeaders httpHeaders) { //الاستشارات يلي بيقدر يجاوب عليهاالدكتور
        Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User doctor = userRepository.findById(doctorId).orElseThrow();
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNullAndSpecializationId(doctor.getSpecialization().getId());
        //for other: get the other and then merge the two consultations list
//        List<Consultation> consultationListTwo = consultationRepository.findAllByConsultationAnswerIsNullAndSpecializationId();

        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }
}
