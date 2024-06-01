package com.example.SensorRestApiService.it;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.MeasurementRepository;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.utils.DataUtils;
import com.example.SensorRestApiService.utils.dto.MeasurementTestDto;
import com.example.SensorRestApiService.utils.dto.SensorTestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItMeasurementControllerV1Tests extends AbstractRestControllerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @BeforeEach
    public void setUp() {
        sensorRepository.deleteAll();
        measurementRepository.deleteAll();
    }

    @Test
    @DisplayName("Test get all measurements functionality")
    public void givenThreeMeasurements_whenGetAllMeasurements_thenSuccessResponse() throws Exception {
        //given
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);

        Measurement measurement1 = DataUtils.getTemperatureMeasurementTransient();
        measurement1.setSensor(sensor);
        Measurement measurement2 = DataUtils.getTemperatureMeasurementTransient();
        measurement2.setSensor(sensor);
        Measurement measurement3 = DataUtils.getTemperatureMeasurementTransient();
        measurement3.setSensor(sensor);

        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);
        measurementRepository.saveAll(measurements);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurements")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Test save measurement functionality")
    public void givenMeasurementDto_whenSaveMeasurement_thenSuccessResponse() throws Exception {
        //given
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensorRepository.save(sensor);

        SensorTestDto obtainedSensorDto = DataUtils.getTemperatureSensorDtoPersisted();
        MeasurementTestDto measurementDto = DataUtils.getTemperatureMeasurementDtoTransient();
        measurementDto.setSensor(obtainedSensorDto);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/measurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test save measurement without sensor functionality")
    public void givenMeasurementDtoWithoutSensor_whenSaveMeasurement_thenErrorResponse() throws Exception {
        //given
        SensorTestDto obtainedSensorDto = DataUtils.getTemperatureSensorDtoPersisted();
        MeasurementTestDto measurementDto = DataUtils.getTemperatureMeasurementDtoTransient();
        measurementDto.setSensor(obtainedSensorDto);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/measurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Sensor with this name not found")));
    }

    @Test
    @DisplayName("Test get all rainy days count functionality")
    public void givenMeasurements_whenGetRainyDaysCount_thenSuccessResponse() throws Exception {
        //given
        Measurement measurement1 = DataUtils.getTemperatureMeasurementPersisted();//true
        Measurement measurement2 = DataUtils.getHumidityMeasurementPersisted();//true
        Measurement measurement3 = DataUtils.getPressureMeasurementPersisted();//false
        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);
        measurementRepository.saveAll(measurements);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurements" + "/rainy-days-count")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(2)));
    }
}