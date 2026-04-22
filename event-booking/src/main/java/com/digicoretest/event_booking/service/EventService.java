package com.digicoretest.event_booking.service;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateEventDto;
import com.digicoretest.event_booking.enums.EventStatus;
import com.digicoretest.event_booking.model.Event;
import org.springframework.stereotype.Service;

@Service
public class EventService {
        public ApiResponseDto createEvent(CreateEventDto dto){
            Event newEvent = new Event();

            newEvent.setBookedSeats(0);
            newEvent.setTitle(dto.title());
            newEvent.setTotalSeats(dto.totalSeats());
            newEvent.setStatus(EventStatus.OPEN);
            newEvent.setVenue(dto.venue());
            newEvent.setDescription(dto.description());
            newEvent.setDate(dto.date());


        }

}
