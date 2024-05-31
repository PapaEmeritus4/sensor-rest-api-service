package com.example.SensorRestApiService.dto;

import com.example.SensorRestApiService.entity.Measurement;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Measurement DTO")
public class MeasurementDto {

    @Schema(description = "measurement_id", example = "1")
    private Integer id;

    @Schema(description = "value", example = "24.5")
    @NotNull(message = "Value should not be empty")
    @Min(-100)
    @Max(100)
    private double value;

    @Schema(description = "raining", example = "false")
    @NotNull
    private boolean raining;

    @Schema(description = "sensor", example = "Sensor name")
    private SensorDto sensor;

    @Schema(description = "updated_at", example = "2019-05-15")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Measurement toEntity() {
        return Measurement.builder()
                .id(id)
                .value(value)
                .raining(raining)
                .sensor(sensor.toEntity())
                .updatedAt(updatedAt)
                .build();
    }

    public static MeasurementDto fromEntity(Measurement entity) {
        return MeasurementDto.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .raining(entity.isRaining())
                .sensor(SensorDto.fromEntity(entity.getSensor()))
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}