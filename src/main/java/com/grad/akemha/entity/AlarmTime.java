package com.grad.akemha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alarm_time")
public class AlarmTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalTime alarmTime;

    @OneToMany
    @JoinColumn(name = "alarm_history_id")
    private AlarmHistory alarmHistory;


    public AlarmTime(LocalTime time, Medicine medicine) {
    }
}
