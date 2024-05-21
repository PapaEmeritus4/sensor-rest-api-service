package com.example.SensorRestApiService.repository;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementRepositoryTests {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp() {
        measurementRepository.deleteAll();
        sensorRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save measurement functionality")
    public void givenMeasurementObject_whenSaveMeasurement_thenMeasurementIsCreated() {
        //given
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);
        Measurement measurementToSave = DataUtils.getTemperatureMeasurementTransient();
        measurementToSave.setSensor(sensor);
        //when
        Measurement savedMeasurement = measurementRepository.save(measurementToSave);
        //then
        assertThat(savedMeasurement).isNotNull();
    }

    @Test
    @DisplayName("Test find all measurements functionality")
    public void givenFewMeasurements_whenFindAll_thenMeasurementsIsReturned() {
        //given
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);

        Measurement measurement1 = DataUtils.getTemperatureMeasurementTransient();
        Measurement measurement2 = DataUtils.getTemperatureMeasurementTransient();
        Measurement measurement3 = DataUtils.getTemperatureMeasurementTransient();
        measurement1.setSensor(sensor);
        measurement2.setSensor(sensor);
        measurement3.setSensor(sensor);

        measurementRepository.saveAll(List.of(measurement1, measurement2, measurement3));
        //when
        List<Measurement> measurements = measurementRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(measurements)).isFalse();
    }
}
