package com.grad.akemha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "additional_record_info")
public class AdditionalRecordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "additionalRecordInfo")
    private List<AdditionalRecordDetails> additionalRecordDetailsList;

    @Column
    private String name;

    @ManyToOne()
    @JoinColumn(name = "additional_record_id")
    private MedicalRecord medicalRecord;
}
