package com.amritraj.reviewms.exceptions;

import com.amritraj.reviewms.payloads.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder().message(msg).success(false
        ).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentExc.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentExc(
            IllegalArgumentExc ex){
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder().message(msg).success(false
        ).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
