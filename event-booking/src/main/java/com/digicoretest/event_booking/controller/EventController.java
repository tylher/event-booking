package com.digicoretest.event_booking.controller;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateEventDto;
import com.digicoretest.event_booking.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Endpoints for creating and retrieving events")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Create a new event", description = "Creates a new event with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or validation failed")
    })
    @PostMapping()
    public ResponseEntity<ApiResponseDto> createNewEvent(@Valid @RequestBody CreateEventDto dto){
        ApiResponseDto response = eventService.createEvent(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all events", description = "Returns a paginated list of all events sorted by date descending")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully")
    })
    @GetMapping()
    public ResponseEntity<ApiResponseDto> getEvents(@PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        ApiResponseDto response = eventService.getEvents(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a single event", description = "Returns a single event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getEvent(@PathVariable String id){
        ApiResponseDto response = eventService.getSingleEvent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
