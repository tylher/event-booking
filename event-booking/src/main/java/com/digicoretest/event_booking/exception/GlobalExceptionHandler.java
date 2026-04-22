package com.digicoretest.event_booking.exception;

import com.digicoretest.event_booking.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                      HttpStatusCode status, WebRequest request){
            Map<String, String> validationErrors = new HashMap<>();
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();

            errors.forEach(objectError -> {
                String fieldName = ((FieldError)objectError).getField();
                String errVal = objectError.getDefaultMessage();

                validationErrors.put(fieldName,errVal);
            });

            return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NotfoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotfoundException(WebRequest webRequest, NotfoundException exception){
            return new ResponseEntity<>(new ErrorResponseDto(
                    exception.getMessage(), HttpStatus.NOT_FOUND, webRequest.getDescription(false), LocalDateTime.now()
            ),HttpStatus.NOT_FOUND);
        }
}
