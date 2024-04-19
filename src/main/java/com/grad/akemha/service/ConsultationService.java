package com.grad.akemha.service;

import com.grad.akemha.dto.consultation.consultationRequest.AnswerConsultationRequest;
import com.grad.akemha.dto.consultation.consultationRequest.ConsultationRequest;
import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.ConsultationRepository;
import com.grad.akemha.repository.SpecializationRepository;
import com.grad.akemha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ConsultationRes> getAllConsultations() {
        List<Consultation> consultationList = consultationRepository.findAll();
//        System.out.println("listtttttttttt ********************************** " + consultationList); // this line caused stackOverFlow in memory !!!!!!
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
        Consultation consultation = Consultation.builder()
                .title(request.getTitle())
                .consultationText(request.getConsultationText())
                .specialization(specialization)
                .consultationStatus(ConsultationStatus.ACTIVE)
                .beneficiary(beneficiary)
                .consultationType(request.getConsultationType())
//                .img(request.getImg())  //TODO: add MULTIPLE imgS
                .build();
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
}
