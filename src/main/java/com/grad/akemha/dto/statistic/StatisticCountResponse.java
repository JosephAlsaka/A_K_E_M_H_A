package com.grad.akemha.dto.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticCountResponse {
    private int year;
    private int month;
    private long count;

    public StatisticCountResponse(int year, int month, long count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }


}
