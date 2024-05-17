package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.MeasurementRepository;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Override
    public List<Measurement> getMeasurements() {
        return measurementRepository.findAll();
    }

    @Override
    @Transactional
    public Measurement saveMeasurement(Measurement measurement) {
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName());

        if (Objects.isNull(sensor)) {
            throw new SensorNotFoundException("Sensor with this name not found");
        }

        measurement.setSensor(sensor);
        measurement.setUpdatedAt(LocalDateTime.now());
        return measurementRepository.save(measurement);
    }

    @Override
    public Long getRainyDaysCount() {
       return measurementRepository.findAll().stream().filter(Measurement::isRaining).count();
    }
}