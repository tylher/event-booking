package com.digicoretest.event_booking.mappers;

import com.digicoretest.event_booking.dto.EventDto;
import com.digicoretest.event_booking.model.Event;
import org.springframework.format.datetime.DateFormatter;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EventMapper {
    public static EventDto toEventDto(Event event){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        EventDto dto = new EventDto();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setVenue(event.getVenue());
        dto.setDate(event.getDate().format(formatter));
        dto.setTotalSeats(event.getTotalSeats());
        dto.setBookedSeats(event.getBookedSeats());

        return dto;
    }
}
