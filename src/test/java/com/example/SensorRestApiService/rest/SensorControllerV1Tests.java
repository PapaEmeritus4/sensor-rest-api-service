package com.example.SensorRestApiService.rest;

import com.example.SensorRestApiService.dto.SensorDto;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.service.SensorService;
import com.example.SensorRestApiService.util.exception.SensorWithDuplicateNameException;
import com.example.SensorRestApiService.utils.DataUtils;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SensorControllerV1.class)
public class SensorControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorService sensorService;

    @Test
    @DisplayName("Test create sensor functionality")
    public void givenSensorDto_whenCreateSensor_thenSuccessResponse() throws Exception {
        //given
        SensorDto sensorDto = DataUtils.getTemperatureSensorDtoTransient();
        Sensor sensor = DataUtils.getTemperatureSensorPersisted();
        BDDMockito.given(sensorService.saveSensor(any(Sensor.class)))
                .willReturn(sensor);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.name", CoreMatchers.is(sensor.getName())));
    }

    @Test
    @DisplayName("Test create sensor with duplicate name functionality")
    public void givenSensorDtoWithDuplicateName_whenCreateSensor_thenErrorResponse() throws Exception {
        //given
        SensorDto sensorDto = DataUtils.getTemperatureSensorDtoPersisted();
        BDDMockito.given(sensorService.saveSensor(any(Sensor.class)))
                .willThrow(new SensorWithDuplicateNameException("Sensor with this name already exists"));
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(jsonPath("$.status", CoreMatchers.is(409)))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Sensor with this name already exists")));
    }
}