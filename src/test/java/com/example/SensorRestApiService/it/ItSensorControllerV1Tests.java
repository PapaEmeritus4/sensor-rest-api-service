package com.example.SensorRestApiService.it;

import com.example.SensorRestApiService.dto.SensorDto;
import com.example.SensorRestApiService.entity.Sensor;
import com.example.SensorRestApiService.repository.SensorRepository;
import com.example.SensorRestApiService.utils.DataUtils;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItSensorControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp() {
        sensorRepository.deleteAll();
    }

    @Test
    @DisplayName("Test sensor registration functionality")
    public void givenSensorDto_whenRegisterSensor_thenSuccessResponse() throws Exception {
        //given
        SensorDto sensorDto = DataUtils.getTemperatureSensorDtoTransient();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(sensorDto.getName())));

    }

    @Test
    @DisplayName("Test sensor registration with duplicate name functionality")
    public void givenSensorDtoWithDuplicateName_whenRegisterSensor_thenErrorResponse() throws Exception {
        //given
        String duplicateName = "duplicateName";
        Sensor sensor = DataUtils.getTemperatureSensorTransient();
        sensor.setName(duplicateName);
        sensorRepository.save(sensor);
        SensorDto sensorDto = DataUtils.getTemperatureSensorDtoTransient();
        sensorDto.setName(duplicateName);

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(409)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Sensor with this name already exists")));
    }
}