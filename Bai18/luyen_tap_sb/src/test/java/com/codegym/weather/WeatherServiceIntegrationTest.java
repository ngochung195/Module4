package com.codegym.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnWeatherWhenCredentialsAndLocationAreValid() throws Exception {
        mockMvc.perform(get("/weather/user/123456/hanoi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("hanoi"))
                .andExpect(jsonPath("$.cityName").value("Hà Nội"))
                .andExpect(jsonPath("$.temperature").isNumber())
                .andExpect(jsonPath("$.humidity").isNumber())
                .andExpect(jsonPath("$.weatherCondition").isNotEmpty());
    }

    @Test
    void shouldMatchLocationCaseInsensitively() throws Exception {
        mockMvc.perform(get("/weather/admin/admin123/DaNang"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("danang"))
                .andExpect(jsonPath("$.cityName").value("Đà Nẵng"));
    }

    @Test
    void shouldReturn400WhenLocationDoesNotExist() throws Exception {
        mockMvc.perform(get("/weather/user/123456/khongtontai"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.location").value("khongtontai"));
    }

    @Test
    void shouldReturn401WhenPasswordIsWrong() throws Exception {
        mockMvc.perform(get("/weather/user/wrongpass/hanoi"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void shouldReturn401WhenUsernameDoesNotExist() throws Exception {
        mockMvc.perform(get("/weather/unknown/123456/hanoi"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void shouldReturn401WhenPathHasNoCredentials() throws Exception {
        mockMvc.perform(get("/weather/hanoi"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }
}
