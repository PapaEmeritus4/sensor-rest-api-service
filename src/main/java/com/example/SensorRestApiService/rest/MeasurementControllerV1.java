package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.dto.ErrorDto;
import com.example.SensorRestApiService.dto.MeasurementDto;
import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.service.MeasurementService;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurements")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name = "Measurement controller V1", description = "Measurement API")
public class MeasurementControllerV1 {

    private final MeasurementService measurementService;

    @GetMapping
    @Operation(summary = "Get all measurements")
    public ResponseEntity<List<MeasurementDto>> getMeasurements() {
        List<Measurement> entities = measurementService.getMeasurements();
        List<MeasurementDto> dtos = entities.stream()
                .map(MeasurementDto::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping()
    @Operation(summary = "Add measurement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"code\": 404, \"message\": \"Sensor with this name not found\"}"),
                            schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"code\": 400, \"message\": \"Bad request\"}"),
                            schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MeasurementDto> addMeasurement(@RequestBody @Valid MeasurementDto dto) {
        Measurement measurement = dto.toEntity();
        Measurement addedMeasurement = measurementService.saveMeasurement(measurement);
        MeasurementDto result = MeasurementDto.fromEntity(addedMeasurement);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rainy-days-count")
    @Operation(summary = "Get all rainy days count")
    public ResponseEntity<Long> getRainyDaysCount() {
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
}