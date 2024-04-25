package com.grad.akemha.dto.medical_record;


import com.grad.akemha.entity.AdditionalRecordInfo;
import com.grad.akemha.entity.enums.BloodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordRequest {
    @NotNull
    private Boolean coffee;
    @NotNull
    private Boolean alcohol;
    @NotNull
    private Boolean married;
    @NotNull
    private Boolean smoker;
    @NotNull
    private Boolean covidVaccine;
    @NotNull
    private Double height;
    @NotNull
    private Double weight;
    @NotNull
    private BloodType bloodType;

    private List<AdditionalRecordInfoRequest> additionalRecordInfoRequest;
}
