package com.example.SensorRestApiService.util.advice;

import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SensorAndMeasurementControllerAdvice {

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<String> handleSensorNotFoundException(SensorNotFoundException e) {
        String response = e.getMessage();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SensorWithDuplicateNameException.class)
    public ResponseEntity<String> handleSensorWithDuplicateNameException(SensorWithDuplicateNameException e) {
        String response = e.getMessage();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}