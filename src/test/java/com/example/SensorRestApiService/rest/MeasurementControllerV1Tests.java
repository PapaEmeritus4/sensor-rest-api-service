package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.entity.Measurement;
import com.example.SensorRestApiService.service.MeasurementService;
import com.example.SensorRestApiService.util.exception.SensorNotFoundException;
import com.example.SensorRestApiService.utils.DataUtils;
import com.example.SensorRestApiService.utils.dto.MeasurementTestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeasurementControllerV1.class)
public class MeasurementControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasurementService measurementService;

    @Test
    @DisplayName("Test get all measurements functionality")
    public void givenMeasurements_whenGetMeasurements_thenSuccessResponse() throws Exception {
        //given
        Measurement measurement1 = DataUtils.getTemperatureMeasurementPersisted();
        measurement1.setSensor(DataUtils.getTemperatureSensorPersisted());
        Measurement measurement2 = DataUtils.getHumidityMeasurementPersisted();
        measurement2.setSensor(DataUtils.getHumiditySensorPersisted());
        Measurement measurement3 = DataUtils.getPressureMeasurementPersisted();
        measurement3.setSensor(DataUtils.getPressureSensorPersisted());
        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);

        BDDMockito.given(measurementService.getMeasurements())
                .willReturn(measurements);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurements")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Test save measurement functionality")
    public void givenMeasurementDto_whenSaveMeasurement_thenSuccessResponse() throws Exception {
        //given
        MeasurementTestDto measurementDto = DataUtils.getTemperatureMeasurementDtoTransient();
        measurementDto.setSensor(DataUtils.getTemperatureSensorDtoPersisted());
        Measurement measurement = DataUtils.getTemperatureMeasurementPersisted();
        measurement.setSensor(DataUtils.getTemperatureSensorPersisted());

        BDDMockito.given(measurementService.saveMeasurement(any(Measurement.class)))
                .willReturn(measurement);
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
        MeasurementTestDto measurementDto = DataUtils.getTemperatureMeasurementDtoTransient();
        measurementDto.setSensor(DataUtils.getTemperatureSensorDtoPersisted());

        BDDMockito.given(measurementService.saveMeasurement(any(Measurement.class)))
                .willThrow(new SensorNotFoundException("Sensor with this name not found"));
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
        long expectedRainyDaysCount = measurements.stream().filter(Measurement::isRaining).count(); // 2

        BDDMockito.given(measurementService.getRainyDaysCount())
                .willReturn(expectedRainyDaysCount);//2
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurements" + "/rainy-days-count")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(2)));
    }
}