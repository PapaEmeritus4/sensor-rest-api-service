package com.example.SensorRestApiService.dto;

import com.example.SensorRestApiService.entity.Measurement;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Schema(description = "sensor")
    private SensorDto sensor;

    public Measurement toEntity() {
        return Measurement.builder()
                .value(value)
                .raining(raining)
                .sensor(sensor.toEntity())
                .build();
    }

    public static MeasurementDto fromEntity(Measurement entity) {
        return MeasurementDto.builder()
                .value(entity.getValue())
                .raining(entity.isRaining())
                .sensor(SensorDto.fromEntity(entity.getSensor()))
                .build();
    }
}