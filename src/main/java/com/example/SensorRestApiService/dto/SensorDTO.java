package com.example.SensorRestApiService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotNull(message = "Name of SENSOR should not be empty")
    @Size(min = 3, max = 30, message = "Name of SENSOR should be between 2 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
