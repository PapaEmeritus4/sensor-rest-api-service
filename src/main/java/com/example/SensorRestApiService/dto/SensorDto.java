package com.example.SensorRestApiService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sensor DTO")
public class SensorDto {

    @Schema(description = "name", example = "Sensor name")
    @NotNull(message = "Name of SENSOR should not be empty")
    @Size(min = 3, max = 30, message = "Name of SENSOR should be between 2 and 30 characters")
    private String name;
}