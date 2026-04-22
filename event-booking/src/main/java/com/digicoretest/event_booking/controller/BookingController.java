package com.digicoretest.event_booking.controller;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateBookingDto;
import com.digicoretest.event_booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Endpoints for managing event seat bookings")
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Book a seat for an event", description = "Creates a booking for the specified event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Seat booked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or validation failed"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    @PostMapping("/events/{id}/bookings")
    public ResponseEntity<ApiResponseDto> bookEventSeat(@Valid @RequestBody CreateBookingDto createBookingDto
            , @PathVariable String id){
        ApiResponseDto responseDto = bookingService.bookEventSeat(createBookingDto,id);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all bookings for an event", description = "Returns a paginated list of bookings for the specified event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/events/{eventId}/bookings")
    public ResponseEntity<ApiResponseDto> bookEventSeat(@PathVariable String eventId, @PageableDefault(size = 20, sort = "date",
            direction = Sort.Direction.DESC) Pageable pageable){
        ApiResponseDto responseDto = bookingService.getEventBookings(eventId, pageable);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Cancel a booking", description = "Cancels an existing booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<ApiResponseDto> cancelBooking(@PathVariable String id){
        ApiResponseDto response = bookingService.cancelBooking(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
