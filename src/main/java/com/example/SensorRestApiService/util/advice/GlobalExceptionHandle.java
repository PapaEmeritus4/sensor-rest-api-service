package com.example.SensorRestApiService.util.advice;

import com.example.SensorRestApiService.dto.ErrorDto;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(SensorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleSensorNotFoundException(SensorNotFoundException e) {
        ErrorDto response = new ErrorDto(404, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SensorWithDuplicateNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDto> handleSensorWithDuplicateNameException(SensorWithDuplicateNameException e) {
        ErrorDto response = new ErrorDto(409, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}