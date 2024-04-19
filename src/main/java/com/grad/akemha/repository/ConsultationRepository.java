package com.grad.akemha.repository;

import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findAllBySpecialization_SpecializationType(Optional<Specialization> specialization);
    Optional<Consultation> findById(Long id);

    @Query("SELECT c FROM Consultation c WHERE c.consultationText LIKE %:keyword%")
    List<Consultation> findByKeywordInConsultationText(String keyword); //TODO make the search in title also.

    List<Consultation> findAllByConsultationAnswerIsNotNull();

    List<Consultation> findAllByConsultationAnswerIsNotNullAndSpecializationId(Long specializationId);

    List<Consultation> findAllBySpecialization_Id(Optional<Specialization> specialization);

    List<Consultation> findBySpecializationId(Long specializationId);

}
