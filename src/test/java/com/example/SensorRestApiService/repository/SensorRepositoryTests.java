package com.example.SensorRestApiService.repository;

import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SensorRepositoryTests {

    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp() {
        sensorRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save sensor functionality")
    public void givenSensorObject_whenSaveSensor_thenSensorIsCreated() {
        //given
        Sensor sensorToSave = DataUtils.getHumiditySensorTransient();
        //when
        Sensor savedSensor = sensorRepository.save(sensorToSave);
        //then
        assertThat(savedSensor).isNotNull();
    }

    @Test
    @DisplayName("Test find sensor by name functionality")
    public void givenSensorSaved_whenFindSensorByName_thenSensorIsFound() {
        //given
        Sensor sensor = DataUtils.getHumiditySensorTransient();
        sensorRepository.save(sensor);
        //when
        Sensor obtainedSensor = sensorRepository.findByName(sensor.getName());
        //then
        assertThat(obtainedSensor).isNotNull();
        assertThat(obtainedSensor.getName()).isEqualTo(sensor.getName());
    }
}