package com.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

    @NotNull
    private Long guestId;

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDate checkIn;
    private LocalDate checkOut;

}
