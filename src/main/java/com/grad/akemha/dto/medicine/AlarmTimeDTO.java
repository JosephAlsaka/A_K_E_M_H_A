package com.grad.akemha.dto.medicine;

import com.grad.akemha.entity.AlarmTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmTimeDTO {
    private LocalTime time;
    private boolean isTaken;

    public AlarmTimeDTO(AlarmTime alarmTime) {
        this.time = alarmTime.getTime();
        if(alarmTime.getAlarmHistory().)
        this.isTaken = alarmTime.isTaken();
    }
}
