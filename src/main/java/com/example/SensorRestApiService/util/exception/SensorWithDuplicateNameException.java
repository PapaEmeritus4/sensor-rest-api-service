package com.example.SensorRestApiService.util.exception;

public class SensorWithDuplicateNameException extends RuntimeException {
    public SensorWithDuplicateNameException(String message) {
        super(message);
    }
}