package com.example.SensorRestApiService.utils;

import com.example.SensorRestApiService.dto.MeasurementDto;
import com.example.SensorRestApiService.dto.SensorDto;
import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;

import java.time.LocalDateTime;

public class DataUtils {

    public static Sensor getTemperatureSensorTransient() {
        return Sensor.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static Sensor getHumiditySensorTransient() {
        return Sensor.builder()
                .name("Humidity Sensor")
                .build();
    }

    public static Sensor getPressureSensorTransient() {
        return Sensor.builder()
                .name("Pressure Sensor")
                .build();
    }

    public static Sensor getTemperatureSensorPersisted() {
        return Sensor.builder()
                .id(1)
                .name("Temperature Sensor")
                .build();
    }

    public static Sensor getHumiditySensorPersisted() {
        return Sensor.builder()
                .id(2)
                .name("Humidity Sensor")
                .build();
    }

    public static Sensor getPressureSensorPersisted() {
        return Sensor.builder()
                .id(3)
                .name("Pressure Sensor")
                .build();
    }

    public static SensorDto getTemperatureSensorDtoTransient() {
        return SensorDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorDto getHumiditySensorDtoTransient() {
        return SensorDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorDto getPressureSensorDtoTransient() {
        return SensorDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorDto getTemperatureSensorDtoPersisted() {
        return SensorDto.builder()
                .id(1)
                .name("Temperature Sensor")
                .build();
    }

    public static SensorDto getHumiditySensorDtoPersisted() {
        return SensorDto.builder()
                .id(2)
                .name("Temperature Sensor")
                .build();
    }

    public static SensorDto getPressureSensorDtoPersisted() {
        return SensorDto.builder()
                .id(3)
                .name("Temperature Sensor")
                .build();
    }

    public static Measurement getTemperatureMeasurementTransient() {
        return Measurement.builder()
                .value(25.0)
                .raining(false)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getHumidityMeasurementTransient() {
        return Measurement.builder()
                .value(55.0)
                .raining(false)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getPressureMeasurementTransient() {
        return Measurement.builder()
                .value(80.25)
                .raining(false)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getTemperatureMeasurementPersisted() {
        return Measurement.builder()
                .id(1)
                .value(25.0)
                .raining(true)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getHumidityMeasurementPersisted() {
        return Measurement.builder()
                .id(2)
                .value(55.0)
                .raining(true)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Measurement getPressureMeasurementPersisted() {
        return Measurement.builder()
                .id(3)
                .value(1013.25)
                .raining(false)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MeasurementDto getTemperatureMeasurementDtoTransient() {
        return MeasurementDto.builder()
                .value(25.0)
                .raining(false)
                .build();
    }

    public static MeasurementDto getHumidityMeasurementDtoTransient() {
        return MeasurementDto.builder()
                .value(55.0)
                .raining(false)
                .build();
    }

    public static MeasurementDto getPressureMeasurementDtoTransient() {
        return MeasurementDto.builder()
                .value(80.25)
                .raining(false)
                .build();
    }

    public static MeasurementDto getTemperatureMeasurementDtoPersisted() {
        return MeasurementDto.builder()
                .id(1)
                .value(25.0)
                .raining(false)
                .build();
    }

    public static MeasurementDto getHumidityMeasurementDtoPersisted() {
        return MeasurementDto.builder()
                .id(2)
                .value(55.0)
                .raining(false)
                .build();
    }

    public static MeasurementDto getPressureMeasurementDtoPersisted() {
        return MeasurementDto.builder()
                .id(3)
                .value(80.25)
                .raining(false)
                .build();
    }
}