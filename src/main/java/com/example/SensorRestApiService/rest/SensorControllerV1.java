package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.dto.ErrorDto;
import com.example.SensorRestApiService.dto.SensorDto;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.service.SensorService;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name = "Sensor controller V1", description = "Sensor API")
public class SensorControllerV1 {

    private final SensorService sensorService;

    @PostMapping()
    @Operation(summary = "Sensor registration")
    public ResponseEntity<?> registerSensor(@RequestBody @Valid SensorDto dto) {
        Sensor sensor = dto.toEntity();
        Sensor registratedSensor = sensorService.saveSensor(sensor);
        SensorDto result = SensorDto.fromEntity(registratedSensor);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(SensorWithDuplicateNameException.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        HttpStatus status;
         if (e instanceof SensorWithDuplicateNameException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorDto errorDto = ErrorDto.builder()
                .status(status.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }
}