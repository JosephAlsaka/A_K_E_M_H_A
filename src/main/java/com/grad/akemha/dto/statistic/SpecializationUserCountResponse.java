package com.grad.akemha.dto.statistic;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SpecializationUserCountResponse {
    private String specializationType;
    private Long userCount;
}
