package com.digicoretest.event_booking.service;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.BookingResponseDto;
import com.digicoretest.event_booking.dto.CreateBookingDto;
import com.digicoretest.event_booking.enums.BookingStatus;
import com.digicoretest.event_booking.enums.EventStatus;
import com.digicoretest.event_booking.exception.NotfoundException;
import com.digicoretest.event_booking.mappers.BookingMapper;
import com.digicoretest.event_booking.model.Booking;
import com.digicoretest.event_booking.model.Event;
import com.digicoretest.event_booking.repository.BookingRepository;
import com.digicoretest.event_booking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public ApiResponseDto bookEventSeat(CreateBookingDto createBookingDto, String eventId){
        // find existing event or throw exception
        Event event = eventRepository.findById(eventId).orElseThrow(
                ()->  new NotfoundException("Event")
        );

        if(bookingRepository.existsByEvent_IdAndAttendeeEmail(eventId,createBookingDto.getAttendeeEmail())){
            throw new IllegalArgumentException("Booking by the email, %s, already exists".formatted(createBookingDto.getAttendeeEmail()));
        }

        // check if all seats have not been booked
        if(event.getStatus().equals(EventStatus.CLOSED)||event.getBookedSeats().equals(event.getTotalSeats())){
            event.setStatus(EventStatus.CLOSED);
            eventRepository.save(event);
            throw new IllegalStateException("Event is fully booked");
        }

        //update the number of seats and save event
        event.setBookedSeats(event.getBookedSeats()+1);
        if (event.getBookedSeats().equals(event.getTotalSeats())) {
            event.setStatus(EventStatus.CLOSED);
        }
        eventRepository.save(event);

        Booking booking = new Booking();

        booking.setBookedAt(LocalDateTime.now());
        booking.setEvent(event);
        booking.setAttendeeEmail(createBookingDto.getAttendeeEmail());
        booking.setAttendeeEmail(createBookingDto.getAttendeeEmail());

        bookingRepository.save(booking);

        return new ApiResponseDto(true, "Event seat booked successfully");
    }


    public ApiResponseDto cancelBooking(String bookingId){
        Booking savedBooking = bookingRepository.findById(bookingId).orElseThrow(
                ()->new NotfoundException("Booking")
        );

        Event event = savedBooking.getEvent();
        savedBooking.setStatus(BookingStatus.CANCELLED);

        event.setBookedSeats(event.getBookedSeats()-1);
        if(event.getStatus().equals(EventStatus.CLOSED)&& event.getBookedSeats() < event.getTotalSeats()){
            event.setStatus(EventStatus.OPEN);
        }
        eventRepository.save(event);
        bookingRepository.save(savedBooking);

        return new ApiResponseDto(true, "Booking cancelled successfully");
    }

    public ApiResponseDto getEventBookings(String eventId, Pageable pageable){
        Page<BookingResponseDto> bookings = bookingRepository.findByEvent_Id(eventId,pageable).map(
                BookingMapper::toBookingDto
        );

        return new ApiResponseDto(true, "Event bookings fetched successfully", bookings.getContent());

    }

}
