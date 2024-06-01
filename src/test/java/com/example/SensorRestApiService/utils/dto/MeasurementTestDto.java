package com.example.SensorRestApiService.utils.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class MeasurementTestDto {

    private Integer id;
    private double value;
    private boolean raining;
    private SensorTestDto sensor;
    private LocalDateTime updatedAt;
}
