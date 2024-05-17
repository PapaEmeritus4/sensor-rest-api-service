package com.example.SensorRestApiService.dto;

import com.example.SensorRestApiService.entity.Measurement;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@RequiredArgsConstructor
public class MeasurementDto {

    private final ModelMapper modelMapper;

    @NotNull(message = "Value should not be empty")
    @Min(-100)
    @Max(100)
    private double value;

    @NotNull
    private boolean raining;

    @NotNull(message = "Name of SENSOR should not be empty")
    private SensorDto sensor;

    public Measurement toEntity(MeasurementDto measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDto fromEntity(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }
}