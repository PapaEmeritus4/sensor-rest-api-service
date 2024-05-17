package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


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
            throw new SensorWithDuplicateNameException("Sensor with this name already exists");
        }

        return sensorRepository.save(sensor);
    }
}