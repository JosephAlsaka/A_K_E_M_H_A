package com.grad.akemha.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "alarm_time")
    private LocalTime alarmTime; // Time variable representing the alarm time
    //alarm.setAlarmTime(LocalTime.of(7, 30)); // Sets alarm time to 7:30 AM

//    private repietDays repietDays; // TODO enum days of a week

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @OneToMany(mappedBy = "alarm")
    private List<AlarmHistory> alarmHistoryList;

}
