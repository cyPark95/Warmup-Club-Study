package com.group.mission.controller.fruit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    void saveFruit() throws Exception {
        // given
        FruitSaveRequest request = new FruitSaveRequest("사과", LocalDate.of(2024, 2, 1), 5000);

        // when
        // then
        mockMvc.perform(post("/api/v1/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void soldFruit() throws Exception {
        // given
        Fruit fruit = saveNotSoldFruit("사과", 5000L);
        FruitSoldRequest request = new FruitSoldRequest(fruit.getId());

        // when
        mockMvc.perform(put("/api/v1/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        Fruit result = fruitRepository.findById(fruit.getId()).orElseThrow();
        assertThat(result.getStatus()).isEqualTo(FruitStatus.SOLD);
    }

    @Test
    void getStatistic() throws Exception {
        // given
        String name = "사과";
        saveSoldFruit(name, 3_000L);
        saveNotSoldFruit(name, 4_000L);
        saveSoldFruit(name, 3_000L);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/fruit/stat")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        FruitStatisticResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), FruitStatisticResponse.class);

        assertThat(response.salesAmount()).isEqualTo(6_000);
        assertThat(response.notSalesAmount()).isEqualTo(4_000);
    }

    private void saveSoldFruit(String name, Long price) {
        Fruit fruit = new Fruit(name, LocalDate.of(2024, 2, 1), price);
        fruit.sold();
        fruitRepository.save(fruit);
    }

    private Fruit saveNotSoldFruit(String name, Long price) {
        Fruit fruit = new Fruit(name, LocalDate.of(2024, 2, 1), price);
        return fruitRepository.save(fruit);
    }
}
