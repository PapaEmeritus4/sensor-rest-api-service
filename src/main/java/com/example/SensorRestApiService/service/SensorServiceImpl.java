package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public Sensor saveSensor(Sensor sensor) {
        Sensor duplicateSensor = sensorRepository.findByName(sensor.getName());

        if(Objects.nonNull(duplicateSensor)) {
            log.error("Sensor with name {} already exists", sensor.getName());
            throw new SensorWithDuplicateNameException("Sensor with this name already exists");
        }

        log.info("Sensor saved successfully: {}", sensor.getName());
        return sensorRepository.save(sensor);
    }
}