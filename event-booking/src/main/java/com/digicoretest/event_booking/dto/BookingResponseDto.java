package com.digicoretest.event_booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponseDto {
    private String eventId;

    private String attendeeName;

    private String attendeeEmail;

    private String bookedAt;
}
