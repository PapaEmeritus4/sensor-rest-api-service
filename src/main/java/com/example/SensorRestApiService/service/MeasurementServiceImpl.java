package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.MeasurementRepository;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Override
    public List<Measurement> getMeasurements() {
        log.info("Measurements were successfully obtained");
        return measurementRepository.findAll();
    }

    @Override
    @Transactional
    public Measurement saveMeasurement(Measurement measurement) {
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName());

        if (Objects.isNull(sensor)) {
            log.error("Sensor with name {} not found", measurement.getSensor().getName());
            throw new SensorNotFoundException("Sensor with this name not found");
        }

        measurement.setSensor(sensor);
        measurement.setUpdatedAt(LocalDateTime.now());
        log.info("Measurement saved successfully: {}", measurement);
        log.info("Related sensor: {}", sensor);
        return measurementRepository.save(measurement);
    }

    @Override
    public Long getRainyDaysCount() {
       return measurementRepository.findAll().stream().filter(Measurement::isRaining).count();
    }
}