package com.group.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.mission.facade.dto.request.SumOfNumbersReqeust;
import com.group.mission.facade.dto.response.CalculationResponse;
import com.group.mission.facade.dto.response.DayOfTheWeekResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculate() throws Exception {
        // given
        int num1 = 10;
        int num2 = 5;

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/calc")
                        .param("num1", String.valueOf(num1))
                        .param("num2", String.valueOf(num2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        CalculationResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CalculationResponse.class
        );

        assertThat(response.add()).isEqualTo(15);
        assertThat(response.minus()).isEqualTo(5);
        assertThat(response.multiply()).isEqualTo(50);
    }

    @Test
    void getDayOfWeek() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 1, 1);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/day-of-the-week")
                        .param("date", String.valueOf(date)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        DayOfTheWeekResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                DayOfTheWeekResponse.class
        );

        assertThat(response.dayOfTheWeek()).isEqualTo("SUN");
    }

    @Test
    void getSumOfNumbers() throws Exception {
        // given
        SumOfNumbersReqeust numbers = new SumOfNumbersReqeust(List.of(1, 2, 3, 4, 5));

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/sum-of-numbers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(numbers)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        assertThat(response).isEqualTo("15");
    }
}
