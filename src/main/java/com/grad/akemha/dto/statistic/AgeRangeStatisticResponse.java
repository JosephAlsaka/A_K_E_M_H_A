package com.grad.akemha.dto.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgeRangeStatisticResponse {
    private String range;
    private Long count;

    public AgeRangeStatisticResponse(String range, Long count) {
        this.range = range;
        this.count = count;
    }
}
