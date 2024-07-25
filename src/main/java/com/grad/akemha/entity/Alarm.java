package com.grad.akemha.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grad.akemha.entity.enums.AlarmRoutine;
import com.grad.akemha.entity.enums.AlarmRoutineType;
import com.grad.akemha.entity.enums.ConsultationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "alarm_time")
    private LocalTime alarmTime; // Time variable representing the alarm time
    //alarm.setAlarmTime(LocalTime.of(7, 30)); // Sets alarm time to 7:30 AM
//
//    @Column(nullable = false)
//    private WeekDays medicineDays; //TODO : i think we will have a problem here because we need to store a list from days not just one day
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmRoutine alarmRoutine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmRoutineType alarmRoutineType;

    @Column()
    private String alarmWeekDay;

    @Column()
    private Integer selectedDayInMonth;
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "medicine_id",nullable = false)
    private Medicine medicine;

//    @OneToMany(mappedBy = "alarm")
//    private List<AlarmHistory> alarmHistoryList;

}
