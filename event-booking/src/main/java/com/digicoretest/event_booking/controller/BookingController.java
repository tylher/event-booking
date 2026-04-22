package com.digicoretest.event_booking.controller;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateBookingDto;
import com.digicoretest.event_booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/events/{id}/bookings")
    public ResponseEntity<ApiResponseDto> bookEventSeat(@Valid @RequestBody CreateBookingDto createBookingDto
            , @PathVariable String id){
        ApiResponseDto responseDto = bookingService.bookEventSeat(createBookingDto,id);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/events/{eventId}/bookings")
    public ResponseEntity<ApiResponseDto> bookEventSeat(@PathVariable String eventId, @PageableDefault(size = 20, sort = "date",
            direction = Sort.Direction.DESC) Pageable pageable){
        ApiResponseDto responseDto = bookingService.getEventBookings(eventId, pageable);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<ApiResponseDto> cancelBooking(@PathVariable String id){
        ApiResponseDto response = bookingService.cancelBooking(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
