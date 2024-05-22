package com.grad.akemha.dto.medicine;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
public class AddAlarmRequest {
    private Date startDate;
    private Date endDate;
    private LocalTime alarmTime;
    private Long medicineId;
}
