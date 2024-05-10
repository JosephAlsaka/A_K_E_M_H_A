package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.post.PostRequest;
import com.grad.akemha.dto.post.PostResponse;
import com.grad.akemha.dto.supervision.response.SupervisionResponse;
import com.grad.akemha.entity.Supervision;
import com.grad.akemha.entity.enums.SupervisionStatus;
import com.grad.akemha.service.SupervisionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supervision")
public class SupervisionController {
    @Autowired
    private SupervisionService supervisionService;

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @PostMapping("/request/{supervisedId}")
    public ResponseEntity<BaseResponse<String>> sendSupervisionRequest(
            @PathVariable Long supervisedId,
            @RequestHeader HttpHeaders httpHeaders
    ) {
        supervisionService.sendSupervisionRequest(supervisedId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Request sent successfully", null));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @GetMapping("/requests")
    public ResponseEntity<BaseResponse<List<SupervisionResponse>>> viewSupervisionRequest(
            @RequestHeader HttpHeaders httpHeaders
    ) {
        List<SupervisionResponse> supervisionList =supervisionService.viewSupervisionRequest(httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Supervision requests successfully", supervisionList));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @PostMapping("/reply/{supervisionId}")
    public ResponseEntity<BaseResponse<String>> replyToSupervisionRequest(
            @PathVariable Long supervisionId,
            @RequestBody SupervisionStatus supervisionStatus,
            @RequestHeader HttpHeaders httpHeaders
    ) {
        supervisionService.replyToSupervisionRequest(supervisionId, supervisionStatus, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "The reply arrived successfully", null));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @GetMapping("/approved/supervised")
    public ResponseEntity<BaseResponse<List<SupervisionResponse>>> getApprovedSupervisionBySupervised(
            @RequestHeader HttpHeaders httpHeaders
    ) {
        List<SupervisionResponse> supervisionList = supervisionService.getApprovedSupervisionBySupervised(httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Approved supervision successfully", supervisionList));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @GetMapping("/approved/supervisor")
    public ResponseEntity<BaseResponse<List<SupervisionResponse>>> getApprovedSupervisionBySupervisor(
            @RequestHeader HttpHeaders httpHeaders
    ) {
        List<SupervisionResponse> supervisionList = supervisionService.getApprovedSupervisionBySupervisor(httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Approved supervision successfully", supervisionList));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('USER')")
    @DeleteMapping("/{supervisionId}")
    public ResponseEntity<BaseResponse<String>> deleteSupervision(
            @PathVariable Long supervisionId
    ) {
        supervisionService.deleteApprovedSupervision(supervisionId);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "The supervision deleted successfully",null));
    }
}
