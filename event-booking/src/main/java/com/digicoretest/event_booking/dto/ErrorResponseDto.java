package com.digicoretest.event_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String errorMessage;
    private HttpStatus errorCode;
    private String apiPath;
    private LocalDateTime errorTime;
}
