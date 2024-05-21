package com.example.SensorRestApiService.utils;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataUtils {

    public static Sensor getTemperatureSensorTransient() {
        return Sensor.builder()
                .name("Temperature Sensor")
                .measurements(new ArrayList<>())
                .build();
    }

    public static Sensor getHumiditySensorTransient() {
        return Sensor.builder()
                .name("Humidity Sensor")
                .measurements(new ArrayList<>())
                .build();
    }

    public static Sensor getPressureSensorTransient() {
        return Sensor.builder()
                .name("Pressure Sensor")
                .measurements(new ArrayList<>())
                .build();
    }

    public static Sensor getTemperatureSensorPersisted() {
        return Sensor.builder()
                .id(1)
                .name("Temperature Sensor")
                .measurements(new ArrayList<>())
                .build();
    }

    public static Sensor getHumiditySensorPersisted() {
        return Sensor.builder()
                .id(2)
                .name("Humidity Sensor")
                .measurements(new ArrayList<>())
                .build();
    }

    public static Sensor getPressureSensorPersisted() {
        return Sensor.builder()
                .id(3)
                .name("Pressure Sensor")
                .measurements(new ArrayList<>())
                .build();
    }


    public static Measurement getTemperatureMeasurementTransient() {
        return Measurement.builder()
                .value(25.0)
                .raining(false)
                .sensor(getTemperatureSensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getHumidityMeasurementTransient() {
        return Measurement.builder()
                .value(55.0)
                .raining(false)
                .sensor(getHumiditySensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getPressureMeasurementTransient() {
        return Measurement.builder()
                .value(80.25)
                .raining(false)
                .sensor(getPressureSensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getTemperatureMeasurementPersisted() {
        return Measurement.builder()
                .id(1)
                .value(25.0)
                .raining(false)
                .sensor(getTemperatureSensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getHumidityMeasurementPersisted() {
        return Measurement.builder()
                .id(2)
                .value(55.0)
                .raining(false)
                .sensor(getHumiditySensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getPressureMeasurementPersisted() {
        return Measurement.builder()
                .id(3)
                .value(1013.25)
                .raining(false)
                .sensor(getPressureSensorPersisted())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}