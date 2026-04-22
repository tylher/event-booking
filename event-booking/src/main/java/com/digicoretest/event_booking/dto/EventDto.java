package com.digicoretest.event_booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EventDto {
    private String title;
    private String description;

    private String date;

    private String venue;

    private int totalSeats;

    private int bookedSeats;
}
