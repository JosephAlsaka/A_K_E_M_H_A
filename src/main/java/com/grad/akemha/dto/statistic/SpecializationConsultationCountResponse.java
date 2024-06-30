package com.grad.akemha.dto.statistic;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class SpecializationConsultationCountResponse {
    private String specializationType;
    private Long consultationCount;

    public SpecializationConsultationCountResponse(String specializationType, Long consultationCount) {
        this.specializationType = specializationType;
        this.consultationCount = consultationCount;
    }

}
