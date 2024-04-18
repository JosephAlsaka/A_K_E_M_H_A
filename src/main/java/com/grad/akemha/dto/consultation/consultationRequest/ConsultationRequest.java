package com.grad.akemha.dto.consultation.consultationRequest;

import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.entity.enums.ConsultationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationRequest {
    private String title;
    private String consultationText;
//    private ConsultationStatus consultationStatus;
    private Long specializationId;
    private ConsultationType consultationType;
//    private str img; //TODO
}
