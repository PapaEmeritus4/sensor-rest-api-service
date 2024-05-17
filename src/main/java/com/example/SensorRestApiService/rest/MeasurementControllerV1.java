package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.dto.ErrorDto;
import com.example.SensorRestApiService.dto.MeasurementDto;
import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.service.MeasurementServiceImpl;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/measurements")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MeasurementControllerV1 {

    private final MeasurementServiceImpl measurementService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getMeasurements() {
        List<MeasurementDto> measurements = measurementService.getMeasurements()
                .stream().map(this::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(measurements);
    }

    @PostMapping()
    public ResponseEntity<?> addMeasurement(@RequestBody @Valid MeasurementDto dto) {
        Measurement measurement = toEntity(dto);
        Measurement addedMeasurement = measurementService.saveMeasurement(measurement);
        MeasurementDto result = fromEntity(addedMeasurement);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rainy-days-count")
    public ResponseEntity<?> getRainyDaysCount() {
        Long rainyDaysCount = measurementService.getRainyDaysCount();
        return ResponseEntity.ok(rainyDaysCount);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        HttpStatus status;
        if (e instanceof SensorNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorDto errorDto = ErrorDto.builder()
                .status(status.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    private Measurement toEntity(MeasurementDto dto) {
        return modelMapper.map(dto, Measurement.class);
    }

    private MeasurementDto fromEntity(Measurement entity) {
        return modelMapper.map(entity, MeasurementDto.class);
    }
}