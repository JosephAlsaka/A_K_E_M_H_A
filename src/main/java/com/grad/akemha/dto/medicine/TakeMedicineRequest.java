package com.grad.akemha.dto.medicine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeMedicineRequest {

    @NotNull(message = "time can't be Null")
    private LocalTime takeTime;

    @NotNull(message = "ringingTime can't be Null")
    private LocalTime ringingTime;
}
