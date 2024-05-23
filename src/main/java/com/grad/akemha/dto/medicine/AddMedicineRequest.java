package com.grad.akemha.dto.medicine;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
public class AddMedicineRequest {
    @NotNull(message = "name can't be Null")
    private String name;

    @NotNull(message = "description can't be Null")
    private String description;

    @NotNull(message = "startDate can't be Null")
    private Date startDate;

    @NotNull(message = "endDate can't be Null")
    private Date endDate;

    @NotNull(message = "alarmTime can't be Null")
    private LocalTime alarmTime;
}
