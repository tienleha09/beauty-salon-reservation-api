package com.project.beautysalonreservationapi.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class, SalonServiceNotFoundException.class})
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<CustomExceptionDetails> handleNotFoundException(Exception ex, WebRequest request){
        CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(Timestamp.valueOf(LocalDateTime.now()),ex.getMessage()
        , request.getDescription(false));
        return new ResponseEntity<>(customExceptionDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error ->{
                    String fieldName = ((FieldError)error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName,message);
                });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({InvalidTimeRangeException.class, BadRequestException.class})
    public ResponseEntity<Object> handleInvalidTimeRangeException(
            Exception ex, WebRequest request) {

        CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(Timestamp.valueOf(LocalDateTime.now()),ex.getMessage()
                , request.getDescription(false));
        return new ResponseEntity<>(customExceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
