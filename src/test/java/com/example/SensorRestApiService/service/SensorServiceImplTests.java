package com.example.SensorRestApiService.service;

import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import com.example.SensorRestApiService.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTests {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test save senor functionality")
    public void givenSensorToSave_whenSaveSensor_thenRepositoryIsCalled() {
        //given
        Sensor sensorToSave = DataUtils.getTemperatureSensorTransient();
        BDDMockito.given(sensorRepository.findByName(anyString()))
                .willReturn(null);
        BDDMockito.given(sensorRepository.save(any(Sensor.class)))
                .willReturn(DataUtils.getTemperatureSensorTransient());
        //when
        Sensor savedSensor = serviceUnderTest.saveSensor(sensorToSave);
        //then
        assertThat(savedSensor).isNotNull();
    }

    @Test
    @DisplayName("Test save senor with duplicate name functionality")
    public void givenSensorToSaveWithDuplicateName_whenSaveSensor_thenExceptionIsThrown() {
        //given
        Sensor sensorToSave = DataUtils.getTemperatureSensorTransient();
        BDDMockito.given(sensorRepository.findByName(anyString()))
                .willReturn(DataUtils.getTemperatureSensorTransient());
        //when
        assertThrows(
                SensorWithDuplicateNameException.class, () -> serviceUnderTest.saveSensor(sensorToSave)
        );
        //then
        verify(sensorRepository, never()).save(any(Sensor.class));
    }
}
