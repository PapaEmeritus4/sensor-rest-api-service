package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Measurement;

import java.util.List;

public interface MeasurementService {

    List<Measurement> getMeasurements();

    Measurement saveMeasurement(Measurement measurement);

    Long getRainyDaysCount();
}
