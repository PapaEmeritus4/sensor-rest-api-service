package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.dto.ErrorDto;
import com.example.SensorRestApiService.dto.SensorDto;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.service.SensorServiceImpl;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SensorControllerV1 {

    private final SensorServiceImpl sensorService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> registrationSensor(@RequestBody @Valid SensorDto dto) {
        Sensor sensor = toEntity(dto);
        Sensor registratedSensor = sensorService.saveSensor(sensor);
        SensorDto result = fromEntity(registratedSensor);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler({SensorNotFoundException.class, SensorWithDuplicateNameException.class})
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        HttpStatus status;
        if (e instanceof SensorNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof SensorWithDuplicateNameException) {
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

    private Sensor toEntity(SensorDto dto) {
        return modelMapper.map(dto, Sensor.class);
    }

    private SensorDto fromEntity(Sensor entity) {
        return modelMapper.map(entity, SensorDto.class);
    }
}