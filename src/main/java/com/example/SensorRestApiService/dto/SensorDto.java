package com.example.SensorRestApiService.dto;

import com.example.SensorRestApiService.entity.Sensor;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Sensor DTO")
public class SensorDto {

    private Integer id;

    @Schema(description = "name", example = "Sensor name")
    @NotNull(message = "Name of SENSOR should not be empty")
    @Size(min = 3, max = 30, message = "Name of SENSOR should be between 2 and 30 characters")
    private String name;

    public Sensor toEntity() {
        return Sensor.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static SensorDto fromEntity(Sensor sensor) {
        return SensorDto.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .build();
    }
}