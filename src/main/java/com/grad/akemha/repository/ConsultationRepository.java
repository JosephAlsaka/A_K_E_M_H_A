package com.grad.akemha.repository;

import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.enums.ConsultationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findAllBySpecialization_SpecializationType(Optional<Specialization> specialization);
    Optional<Consultation> findById(Long id);

    @Query("SELECT c FROM Consultation c WHERE c.consultationText LIKE %:keyword%")
    List<Consultation> findByKeywordInConsultationText(String keyword); //TODO make the search in title also.

    Page<Consultation> findAllByConsultationAnswerIsNotNull(Pageable pageable);

    List<Consultation> findAllByConsultationAnswerIsNotNullAndSpecializationId(Long specializationId, Pageable pageable);

    List<Consultation> findBySpecializationId(Long specializationId, Pageable pageable);

    List<Consultation> findByBeneficiaryIdAndConsultationStatus(Long beneficiaryId, ConsultationStatus consultationStatus);

    List<Consultation> findAllByConsultationAnswerIsNotNullAndBeneficiaryId(Long beneficiaryId, Pageable pageable);

    List<Consultation> findAllByConsultationAnswerIsNullAndSpecializationId(Long specializationId);

    List<Consultation> findAllBySpecializationIsPublicTrue();
    List<Consultation> findAllByConsultationAnswerIsNullAndSpecializationIdOrSpecializationIsPublicTrue(Long specializationId, Pageable pageable);

    List<Consultation> findAllByDoctorId(Long doctorId, Pageable pageable);
}
