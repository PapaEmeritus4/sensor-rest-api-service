package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.MeasurementRepository;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import com.example.SensorRestApiService.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceImplTests {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private MeasurementServiceImpl serviceUnderTest;

    @BeforeEach
    public void setUp() {
        sensorRepository.deleteAll();
        measurementRepository.deleteAll();
    }

    @Test
    @DisplayName("Test get all measurements functionality")
    public void givenThreeMeasurements_whenGetAllMeasurements_thenGetAllMeasurements() {
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);

        Measurement measurement1 = DataUtils.getTemperatureMeasurementPersisted();
        Measurement measurement2 = DataUtils.getTemperatureMeasurementPersisted();
        Measurement measurement3 = DataUtils.getTemperatureMeasurementPersisted();
        measurement1.setSensor(sensor);
        measurement2.setSensor(sensor);
        measurement3.setSensor(sensor);
        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);

        BDDMockito.given(measurementRepository.findAll())
                .willReturn(measurements);
        //when
        List<Measurement> obtainedMeasurements = serviceUnderTest.getMeasurements();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedMeasurements)).isFalse();
        assertThat(obtainedMeasurements.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test save measurement functionality")
    public void givenMeasurementToSave_whenSaveMeasurement_thenRepositoryIsCalled() {
        //given
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);
        Measurement measurementToSave = DataUtils.getTemperatureMeasurementTransient();
        measurementToSave.setSensor(sensor);

        BDDMockito.given(sensorRepository.findByName(anyString()))
                .willReturn(sensor);
        BDDMockito.given(measurementRepository.save(any(Measurement.class)))
                .willReturn(DataUtils.getTemperatureMeasurementTransient());
        //when
        Measurement savedMeasurement = serviceUnderTest.saveMeasurement(measurementToSave);
        //then
        assertThat(savedMeasurement).isNotNull();
    }

    @Test
    @DisplayName("Test save measurement without sensor functionality")
    public void givenMeasurementToSaveWithoutSensor_whenSaveMeasurement_thenExceptionIsThrown() {
        // given
        Measurement measurementToSave = DataUtils.getTemperatureMeasurementTransient();
        Sensor sensor = DataUtils.getTemperatureSensorPersisted();
        measurementToSave.setSensor(sensor);
        BDDMockito.given(sensorRepository.findByName(anyString()))
                .willReturn(null);
        // when
        assertThrows(
                SensorNotFoundException.class, () -> serviceUnderTest.saveMeasurement(measurementToSave)
        );
        // then
        verify(measurementRepository, never()).save(any(Measurement.class));
    }

    @Test
    @DisplayName("Test get all rainy days count functionality")
    public void givenThreeMeasurements_whenGetRainyDaysCount_thenGetAllRainyDaysCount() {
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);

        Measurement measurement1 = DataUtils.getTemperatureMeasurementPersisted();//true
        Measurement measurement2 = DataUtils.getHumidityMeasurementPersisted();//true
        Measurement measurement3 = DataUtils.getPressureMeasurementPersisted();//false
        measurement1.setSensor(sensor);
        measurement2.setSensor(sensor);
        measurement3.setSensor(sensor);
        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);

        BDDMockito.given(measurementRepository.findAll())
                .willReturn(measurements);
        //when
        Long rainyDaysCount = serviceUnderTest.getRainyDaysCount();
        //then
        assertEquals(2L, rainyDaysCount);
    }
}