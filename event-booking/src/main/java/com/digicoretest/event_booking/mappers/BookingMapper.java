package com.digicoretest.event_booking.mappers;

import com.digicoretest.event_booking.dto.BookingResponseDto;
import com.digicoretest.event_booking.model.Booking;

import java.time.format.DateTimeFormatter;

public class BookingMapper {
        public static BookingResponseDto toBookingDto(Booking booking){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return  BookingResponseDto.builder()
                    .bookedAt(booking.getBookedAt().format(formatter))
                    .attendeeEmail(booking.getAttendeeEmail())
                    .eventId(booking.getEvent().getId())
                    .attendeeName(booking.getAttendeeName())
                    .build();
        }
}
