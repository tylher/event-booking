package com.digicoretest.event_booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Locale;

public record CreateEventDto(
    @NotBlank(message = "Event title cannot be blank")
    String title,
    String description,

    @NotNull(message = "Date is required")
    @Future(message = "Date must be a future date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime date,

    @NotBlank(message = "Venue cannot be blank")
    String venue,

    @Positive(message = "Total seats should be greater than zero")
    @NotNull(message = "Total number of seats is required")
    Integer totalSeats

) {
}
