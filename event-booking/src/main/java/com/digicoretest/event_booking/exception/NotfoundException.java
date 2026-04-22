package com.digicoretest.event_booking.exception;

public class NotfoundException extends RuntimeException{
    public NotfoundException(String resourceName){
        super(String.format("%s not found ", resourceName));
    }
}
