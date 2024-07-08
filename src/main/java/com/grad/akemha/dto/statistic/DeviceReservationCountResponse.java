package com.grad.akemha.dto.statistic;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeviceReservationCountResponse {
    private String deviceName;
    private int year;
    private int month;
    private long count;
}
