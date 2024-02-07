package com.example.SensorRestApiService.util.measurement;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String msg) {
        super(msg);
    }
}
