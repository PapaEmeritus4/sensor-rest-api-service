package com.example.SensorRestApiService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Measurement DTO")
public class MeasurementDto {

    @Schema(description = "value", example = "24.5")
    @NotNull(message = "Value should not be empty")
    @Min(-100)
    @Max(100)
    private double value;

    @Schema(description = "raining", example = "false")
    @NotNull
    private boolean raining;

    @Schema(description = "sensor", example = "Sensor name")
    @NotNull(message = "Name of SENSOR should not be empty")
    private SensorDto sensor;
}