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
@Table(name = "alarm_history")
public class AlarmHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalTime takeTime;
    @ManyToOne
    @JoinColumn(name = "alarm_time_id", nullable = false)
    private AlarmTime alarmTime;

}
