package com.digicoretest.event_booking.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateBookingDto {
    @NotBlank(message = "Event id cannot be blank")
    String eventId;

    @NotBlank(message = "Attendee email cannot be blank")
    @Email(message = "Attendee email should be valid")
    String attendeeEmail;

    String attendeeName;
}
