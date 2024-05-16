package com.grad.akemha.service;

import com.grad.akemha.dto.consultation.consultationRequest.AnswerConsultationRequest;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.*;
import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.entity.enums.ConsultationType;
import com.grad.akemha.exception.CloudinaryException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.repository.ConsultationRepository;
import com.grad.akemha.repository.ImageRepository;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.service.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public List<ConsultationRes> getAllConsultations(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Consultation> consultationPage = consultationRepository.findAll(pageable);
        List<ConsultationRes> consultationResponseList = consultationPage.getContent().stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
//        List<Consultation> consultationList = consultationRepository.findAll();
//        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
//        return consultationResponseList;
    }

    public List<ConsultationRes> getConsultationsBySpecializationId(Long specializationId,Integer page) {
        if (specializationRepository.findById(specializationId).isPresent()) {
            Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
            List<Consultation> consultationList = consultationRepository.findBySpecializationId(specializationId,pageable);
            List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
            return consultationResponseList;
        } else {
            throw new NotFoundException("SpecializationId " + specializationId + " is not found");
        }

    }

    public List<ConsultationRes> getAnsweredConsultationsBySpecializationId(Long specializationId, Integer page) {
        if (specializationRepository.findById(specializationId).isPresent()) {
            Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
            List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNullAndSpecializationId(specializationId, pageable);
            List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
            return consultationResponseList;
        } else {
            throw new NotFoundException("SpecializationId " + specializationId + " is not found");
        }
    }

    public List<ConsultationRes> findConsultationsByKeyword(String keyword) {
        List<Consultation> consultationList = consultationRepository.findByKeywordInConsultationText(keyword);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public Consultation postConsultation(HttpHeaders httpHeaders, String title, String consultationText, Long specializationId, ConsultationType consultationType, List<MultipartFile> files) {
        Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User beneficiary = userRepository.findById(beneficiaryId).orElseThrow(() -> new NotFoundException("beneficiary Id: " + beneficiaryId + " is not found"));

        Specialization specialization = specializationRepository.findById(specializationId).orElseThrow(() -> new NotFoundException("specialization Id: " + specializationId + " is not found"));

        // Upload and save multiple images
        List<Image> images = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            images = files.stream().map(file -> {
                Image image = new Image();
                image.setImageUrl(cloudinaryService.uploadFile(file, "Consultations", beneficiaryId.toString()));
                if (image.getImageUrl() == null) {
                    throw new CloudinaryException("Image upload failed");
                }
                return imageRepository.save(image);
            }).collect(Collectors.toList());
        }

        Consultation consultation = Consultation.builder().title(title).consultationText(consultationText).specialization(specialization).consultationStatus(ConsultationStatus.NULL).beneficiary(beneficiary).consultationType(consultationType).images(images).createTime(new Date()).build();
        consultationRepository.save(consultation);
        return consultation;
    }

    public Consultation answerConsultation(AnswerConsultationRequest request, HttpHeaders httpHeaders) {
        Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new NotFoundException("doctor Id: " + doctorId + " is not found"));
        Consultation consultation = consultationRepository.findById(request.consultationId()).orElseThrow(() -> new NotFoundException("consultation not found"));
        consultation.setDoctor(doctor);
        consultation.setConsultationAnswer(request.answer());
        consultation.setConsultationStatus(ConsultationStatus.ARCHIVED);
        consultationRepository.save(consultation);
        return consultation;
    }

    public List<ConsultationRes> getAllAnsweredConsultations(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Consultation> consultationPage = consultationRepository.findAllByConsultationAnswerIsNotNull(pageable);
//        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNull(pageable);
        List<ConsultationRes> consultationResponseList = consultationPage.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPersonalNullConsultations(HttpHeaders httpHeaders) {
        Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        List<Consultation> consultationList = consultationRepository.findByBeneficiaryIdAndConsultationStatus(beneficiaryId, ConsultationStatus.NULL);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPersonalAnsweredConsultations(HttpHeaders httpHeaders, Integer page) {
        Long beneficiaryId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNullAndBeneficiaryId(beneficiaryId, pageable);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getPendingConsultationsForDoctor(HttpHeaders httpHeaders, Integer page) { //الاستشارات يلي بيقدر يجاوب عليهاالدكتور
        Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new NotFoundException("doctor Id: " + doctorId + " is not found"));
//        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNullAndSpecializationId(doctor.getSpecialization().getId());
//        List<Consultation> consultationListTwo = consultationRepository.findAllBySpecializationIsPublicTrue();
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNullAndSpecializationIdOrSpecializationIsPublicTrue(doctor.getSpecialization().getId(),pageable);

        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getBeneficiaryAnsweredConsultations(Long beneficiaryId, Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        List<Consultation> consultationList = consultationRepository.findAllByConsultationAnswerIsNotNullAndBeneficiaryId(beneficiaryId, pageable);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public List<ConsultationRes> getDoctorAnsweredConsultations(HttpHeaders httpHeaders, Integer page) {
        Long doctorId = Long.parseLong(jwtService.extractUserId(httpHeaders));
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        List<Consultation> consultationList = consultationRepository.findAllByDoctorId(doctorId,pageable);
        List<ConsultationRes> consultationResponseList = consultationList.stream().map(consultation -> new ConsultationRes(consultation)).toList();
        return consultationResponseList;
    }

    public void deleteConsultation(Long consultationId) {
        consultationRepository.deleteById(consultationId);
    }

    public Consultation makeConsultationAnonymous(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId).orElseThrow(() -> new NotFoundException("consultation not found"));
        consultation.setConsultationType(ConsultationType.ANONYMOUS);
        consultationRepository.save(consultation);
        return consultation;
    }
}
