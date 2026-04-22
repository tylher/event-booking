package com.digicoretest.event_booking.controller;

import com.digicoretest.event_booking.dto.ApiResponseDto;
import com.digicoretest.event_booking.dto.CreateEventDto;
import com.digicoretest.event_booking.service.EventService;
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

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @PostMapping()
    public ResponseEntity<ApiResponseDto> createNewEvent(@Valid @RequestBody CreateEventDto dto){
        ApiResponseDto response = eventService.createEvent(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDto> getEvents(@PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        ApiResponseDto response = eventService.getEvents(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getEvent(@PathVariable String id){
        ApiResponseDto response = eventService.getSingleEvent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
