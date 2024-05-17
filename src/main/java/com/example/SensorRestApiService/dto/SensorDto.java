package com.example.SensorRestApiService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SensorDto {

    @NotNull(message = "Name of SENSOR should not be empty")
    @Size(min = 3, max = 30, message = "Name of SENSOR should be between 2 and 30 characters")
    private String name;
}