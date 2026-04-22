package com.digicoretest.event_booking.service;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateEventDto;
import com.digicoretest.event_booking.dto.EventDto;
import com.digicoretest.event_booking.enums.EventStatus;
import com.digicoretest.event_booking.exception.NotfoundException;
import com.digicoretest.event_booking.mappers.EventMapper;
import com.digicoretest.event_booking.model.Event;
import com.digicoretest.event_booking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
        public ApiResponseDto createEvent(CreateEventDto dto){
            if(dto.totalSeats()<1){
                throw new IllegalArgumentException("Total seats in the an should be at least one");
            }

            if(dto.date().isBefore(LocalDateTime.now())){
                throw  new IllegalArgumentException("Event date must be in the future");
            }
            Event newEvent = new Event();

            newEvent.setBookedSeats(0);
            newEvent.setTitle(dto.title());
            newEvent.setTotalSeats(dto.totalSeats());
            newEvent.setStatus(EventStatus.OPEN);
            newEvent.setVenue(dto.venue());
            newEvent.setDescription(dto.description());
            newEvent.setDate(dto.date());

            eventRepository.save(newEvent);

            return new ApiResponseDto(true,"Event created successfully");
        }

        public ApiResponseDto getEvents(Pageable pageable){
            Page<EventDto> paginatedEvents = eventRepository.findAll(pageable).map(EventMapper::toEventDto);
            List<EventDto> events =paginatedEvents.getContent();
            return new ApiResponseDto(true, "Events fetched successfully",events);
        }

        public ApiResponseDto getSingleEvent(String id){
            Optional<Event> eventOptional= eventRepository.findById(id);

            if(eventOptional.isPresent()){
                EventDto dto = EventMapper.toEventDto(eventOptional.get());
                return new ApiResponseDto(true, "Event fetched successfully",dto);
            }else {
                throw new NotfoundException("Event");
            }
        }

}
