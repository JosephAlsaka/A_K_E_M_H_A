package com.grad.akemha.service;

import com.grad.akemha.entity.Supervision;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.SupervisionStatus;
import com.grad.akemha.exception.ForbiddenException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.repository.SupervisionRepository;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisionService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupervisionRepository supervisionRepository;

    public void sendSupervisionRequest(Long supervisedId, HttpHeaders httpHeaders) {
        if (supervisedId == null) {
            throw new ForbiddenException("supervisedId is Null");
        }
        User supervisor = jwtService.extractUserFromToken(httpHeaders);
        User supervised = userRepository.findById(supervisedId).orElseThrow(() -> new NotFoundException("user Id: " + supervisedId + " is not found"));
        Supervision supervision = Supervision.builder()
                .supervisor(supervisor)
                .supervised(supervised)
                .supervisionStatus(SupervisionStatus.PENDING)
                .build();
        supervisionRepository.save(supervision);
    }

    public List<Supervision> viewSupervisionRequest(HttpHeaders httpHeaders) {
        User supervised = jwtService.extractUserFromToken(httpHeaders);
        List<Supervision> supervisionList = supervisionRepository.findBySupervisionStatusAndSupervised(SupervisionStatus.PENDING, supervised);
        return supervisionList;
    }

    public void replyToSupervisionRequest(Long supervisionId, SupervisionStatus supervisionStatus, HttpHeaders httpHeaders) {
        if (supervisionStatus == null) {
            throw new ForbiddenException("supervisedId is Null");
        }
        if (supervisionStatus != SupervisionStatus.APPROVED || supervisionStatus != SupervisionStatus.REJECTED) {
            throw new ForbiddenException("Wrong information");
        }
        Supervision supervision= supervisionRepository.findById(supervisionId).orElseThrow(() -> new NotFoundException("supervisionId: " + supervisionId + " is not found"));
        supervision.setSupervisionStatus(supervisionStatus);
    }

    public List<Supervision> getApprovedSupervisionBySupervised(HttpHeaders httpHeaders) {
        User supervised = jwtService.extractUserFromToken(httpHeaders);
        List<Supervision> supervisionList = supervisionRepository.findBySupervisionStatusAndSupervised(SupervisionStatus.APPROVED, supervised);
        return supervisionList;
    }

    public void deleteApprovedSupervision(Long supervisionId) {
        supervisionRepository.deleteById(supervisionId);
    }

    public List<Supervision> getApprovedSupervisionBySupervisor(HttpHeaders httpHeaders) {
        User supervisor = jwtService.extractUserFromToken(httpHeaders);
        List<Supervision> supervisionList = supervisionRepository.findBySupervisionStatusAndSupervisor(SupervisionStatus.APPROVED, supervisor);
        return supervisionList;
    }
}