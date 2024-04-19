package com.grad.akemha.dto.consultation.consultationResponse;

import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.enums.ConsultationStatus;
import com.grad.akemha.entity.enums.ConsultationType;
import com.grad.akemha.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationRes {
    private Long id;
    private String consultationText;
    private String consultationAnswer;
//    private String status;
    private ConsultationStatus consultationStatus;
    private Specialization specialization;
    private UserInConsultationRes beneficiary;
    private UserInConsultationRes doctor;

    private ConsultationType consultationType;
 // TODO: images?
    public ConsultationRes(Consultation consultation) {
        this.id = consultation.getId();
        this.consultationText = consultation.getConsultationText();
        this.consultationAnswer = consultation.getConsultationAnswer();
//        this.status = consultation.getStatus();
        this.consultationStatus = consultation.getConsultationStatus();
        this.consultationType = consultation.getConsultationType();
        this.specialization = consultation.getSpecialization();
        this.beneficiary = new UserInConsultationRes(consultation.getBeneficiary().getName(),
                consultation.getBeneficiary().getProfileImage(),
                consultation.getBeneficiary().getGender());
        if (consultation.getDoctor() != null){
            this.doctor = new UserInConsultationRes(consultation.getDoctor().getName(),
                    consultation.getDoctor().getProfileImage(),
                    consultation.getDoctor().getGender());
        }

//        this.specialization = consultation.getTextFiles().stream().map(file -> new FileResponse(file)).toList();
    }

    record UserInConsultationRes(String name, String profileImg, Gender gender) {
    }
}


//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//class UserInConsultationRes{
//    private String name;
//    private String profileImg;
//    private Gender gender;
//    public UserInConsultationRes(User user) {
//        this.name = user.getName();
//        this.profileImg = user.getProfileImage();
//        this.gender = user.getGender();
//    }
//}