package com.example.SensorRestApiService.dto;

import jakarta.validation.constraints.*;

public class MeasurementDTO {

    @NotNull(message = "Value should not be empty")
    @Min(-100)
    @Max(100)
    private double value;

    @NotNull
    private boolean raining;

    @NotNull(message = "Name of SENSOR should not be empty")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
