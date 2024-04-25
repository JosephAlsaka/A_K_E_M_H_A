package com.grad.akemha.service;

import com.grad.akemha.dto.medical_record.AdditionalRecordInfoRequest;
import com.grad.akemha.dto.medical_record.AdditionalRecordInfoResponse;
import com.grad.akemha.dto.medical_record.MedicalRecordRequest;
import com.grad.akemha.dto.medical_record.MedicalRecordResponse;
import com.grad.akemha.entity.AdditionalRecordInfo;
import com.grad.akemha.entity.MedicalRecord;
import com.grad.akemha.entity.User;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.repository.MedicalRecordRepository;
import com.grad.akemha.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final JwtService jwtService;


    // Read
    public MedicalRecordResponse getLastMedicalRecord(HttpHeaders httpHeaders) {
        // finding the user to get it's medical record
        User user = jwtService.extractUserFromToken(httpHeaders);
//        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findLastMedicalRecordByUser(user, PageRequest.of(0, 1));
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findFirstByUserOrderByCreateTimeDesc(user);
        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord medicalRecord = optionalMedicalRecord.get();
            // getting the additional record info as a AdditionalRecordInfoResponse object
            List<AdditionalRecordInfoResponse> additionalRecordInfoDataList = medicalRecord.getAdditionalRecordInfo().stream().map(AdditionalRecordInfoResponse::new).toList();
            return MedicalRecordResponse
                    .builder()
                    .id(medicalRecord.getId())
                    .coffee(medicalRecord.getCoffee())
                    .alcohol(medicalRecord.getAlcohol())
                    .married(medicalRecord.getMarried())
                    .smoker(medicalRecord.getSmoker())
                    .covidVaccine(medicalRecord.getCovidVaccine())
                    .height(medicalRecord.getHeight())
                    .weight(medicalRecord.getWeight())
                    .bloodType(medicalRecord.getBloodType())
                    .createTime(medicalRecord.getCreateTime())
                    .additionalRecordInfoResponse(additionalRecordInfoDataList)
                    .build();
        } else {
            throw new NotFoundException("Medical Record is Not Found");
        }
    }

    public List<MedicalRecord> getAllMedicalRecord(Long userId) {
        return medicalRecordRepository.findAllByUserId(userId);
    }

    // Create
    public MedicalRecordResponse createMedicalRecord(@NotNull MedicalRecordRequest medicalRecordRequest,
                                                     HttpHeaders httpHeaders) {
        User user = jwtService.extractUserFromToken(httpHeaders);
        MedicalRecord medicalRecord = new MedicalRecord();

        List<AdditionalRecordInfo> list = settingAdditionalRecordInfoEntityList(
                medicalRecordRequest.getAdditionalRecordInfoRequest(),
                medicalRecord
        );

        medicalRecord.setCoffee(medicalRecordRequest.getCoffee());
        medicalRecord.setAlcohol(medicalRecordRequest.getAlcohol());
        medicalRecord.setMarried(medicalRecordRequest.getMarried());
        medicalRecord.setSmoker(medicalRecordRequest.getSmoker());
        medicalRecord.setCovidVaccine(medicalRecordRequest.getCovidVaccine());
        medicalRecord.setHeight(medicalRecordRequest.getHeight());
        medicalRecord.setWeight(medicalRecordRequest.getWeight());
        medicalRecord.setBloodType(medicalRecordRequest.getBloodType());
        medicalRecord.setUser(user);
        medicalRecord.setCreateTime(new Date());
        medicalRecord.setAdditionalRecordInfo(list);

        medicalRecordRepository.save(medicalRecord);

        List<AdditionalRecordInfoResponse> additionalRecordInfoDataList = list.stream().map(AdditionalRecordInfoResponse::new).toList();

        return MedicalRecordResponse
                .builder()
                .id(medicalRecord.getId())
                .coffee(medicalRecord.getCoffee())
                .alcohol(medicalRecord.getAlcohol())
                .married(medicalRecord.getMarried())
                .smoker(medicalRecord.getSmoker())
                .covidVaccine(medicalRecord.getCovidVaccine())
                .height(medicalRecord.getHeight())
                .weight(medicalRecord.getWeight())
                .bloodType(medicalRecord.getBloodType())
                .createTime(medicalRecord.getCreateTime())
                .additionalRecordInfoResponse(additionalRecordInfoDataList)
                .build();
    }

    public List<AdditionalRecordInfo> settingAdditionalRecordInfoEntityList(List<AdditionalRecordInfoRequest> additionalRecordInfoRequestList,
                                                                            MedicalRecord medicalRecord) {
        List<AdditionalRecordInfo> list = new ArrayList<>();
        for (AdditionalRecordInfoRequest request :
                additionalRecordInfoRequestList
        ) {
            AdditionalRecordInfo additionalRecordInfo = new AdditionalRecordInfo();
            additionalRecordInfo.setName(request.getName());
            additionalRecordInfo.setDescription(request.getDescription());
            additionalRecordInfo.setType(request.getType());
            additionalRecordInfo.setCreateTime(new Date());
            additionalRecordInfo.setMedicalRecord(medicalRecord);
            list.add(additionalRecordInfo);
        }
        return list;
    }

}
