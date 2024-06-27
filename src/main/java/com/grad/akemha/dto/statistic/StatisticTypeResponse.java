package com.grad.akemha.dto.statistic;

import com.grad.akemha.entity.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticTypeResponse {
    private Gender gender;
    private long count;
    private String   percent;

    public StatisticTypeResponse(Gender gender, long count,String  percent) {
        this.gender = gender;
        this.count = count;
        this.percent = percent;
    }
}
