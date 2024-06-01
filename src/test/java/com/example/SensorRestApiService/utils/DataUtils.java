package com.example.SensorRestApiService.utils;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.utils.dto.MeasurementTestDto;
import com.example.SensorRestApiService.utils.dto.SensorTestDto;

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

    public static SensorTestDto getTemperatureSensorDtoTransient() {
        return SensorTestDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorTestDto getHumiditySensorDtoTransient() {
        return SensorTestDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorTestDto getPressureSensorDtoTransient() {
        return SensorTestDto.builder()
                .name("Temperature Sensor")
                .build();
    }

    public static SensorTestDto getTemperatureSensorDtoPersisted() {
        return SensorTestDto.builder()
                .id(1)
                .name("Temperature Sensor")
                .build();
    }

    public static SensorTestDto getHumiditySensorDtoPersisted() {
        return SensorTestDto.builder()
                .id(2)
                .name("Temperature Sensor")
                .build();
    }

    public static SensorTestDto getPressureSensorDtoPersisted() {
        return SensorTestDto.builder()
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

    public static MeasurementTestDto getTemperatureMeasurementDtoTransient() {
        return MeasurementTestDto.builder()
                .value(25.0)
                .raining(false)
                .build();
    }

    public static MeasurementTestDto getHumidityMeasurementDtoTransient() {
        return MeasurementTestDto.builder()
                .value(55.0)
                .raining(false)
                .build();
    }

    public static MeasurementTestDto getPressureMeasurementDtoTransient() {
        return MeasurementTestDto.builder()
                .value(80.25)
                .raining(false)
                .build();
    }

    public static MeasurementTestDto getTemperatureMeasurementDtoPersisted() {
        return MeasurementTestDto.builder()
                .id(1)
                .value(25.0)
                .raining(false)
                .build();
    }

    public static MeasurementTestDto getHumidityMeasurementDtoPersisted() {
        return MeasurementTestDto.builder()
                .id(2)
                .value(55.0)
                .raining(false)
                .build();
    }

    public static MeasurementTestDto getPressureMeasurementDtoPersisted() {
        return MeasurementTestDto.builder()
                .id(3)
                .value(80.25)
                .raining(false)
                .build();
    }
}