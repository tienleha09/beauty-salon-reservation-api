package com.project.beautysalonreservationapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.time.LocalTime;

public class InvalidTimeRangeException extends RuntimeException{
    public InvalidTimeRangeException(LocalTime start, LocalTime end){
        super("Start time at "+ start + " and End time at " + end + " is invalid");
    }
}
