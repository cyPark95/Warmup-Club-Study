package com.group.mission.controller.fruit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.mission.common.ComparisonOperator;
import com.group.mission.domain.fruit.Fruit;
import com.group.mission.domain.fruit.FruitStatus;
import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.response.FruitCountResponse;
import com.group.mission.dto.fruit.response.FruitResponse;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
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

    @BeforeEach
    void cleanUp() {
        fruitRepository.deleteAll();
    }

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
        Fruit fruit = saveNotSoldFruit("사과", LocalDate.of(2024, 2, 1), 5000L);
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
        saveSoldFruit(name, LocalDate.of(2024, 2, 1), 3_000L);
        saveNotSoldFruit(name, LocalDate.of(2024, 2, 1), 4_000L);
        saveSoldFruit(name, LocalDate.of(2024, 2, 1), 3_000L);

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

    @Test
    void getFruitCount() throws Exception {
        // given
        String name = "사과";
        saveSoldFruit(name, LocalDate.of(2024, 2, 1), 3_000L);
        saveNotSoldFruit(name, LocalDate.of(2024, 2, 1), 4_000L);
        saveSoldFruit(name, LocalDate.of(2024, 2, 1), 3_000L);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/fruit/count")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        FruitCountResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), FruitCountResponse.class);

        assertThat(response.count()).isEqualTo(2);
    }

    @Test
    void getFruits() throws Exception {
        // given
        saveSoldFruit("수박", LocalDate.of(2024, 5, 9), 10_000L);
        saveSoldFruit("딸기", LocalDate.of(2024, 5, 7), 4_000L);
        saveNotSoldFruit("사과", LocalDate.of(2024, 4, 28), 4_000L);
        saveNotSoldFruit("귤", LocalDate.of(2024, 4, 30), 2_500L);
        saveNotSoldFruit("바나나", LocalDate.of(2024, 5, 9), 6_000L);

        // when
        MvcResult resultGte = mockMvc.perform(get("/api/v1/fruit/list")
                        .param("option", ComparisonOperator.GTE.name())
                        .param("price", "4000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<FruitResponse> responseGte = objectMapper.readValue(resultGte.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});

        assertThat(responseGte).extracting("name", "price", "warehousingDate")
                .contains(tuple("사과", 4_000L, LocalDate.of(2024, 4, 28)),
                        tuple("바나나", 6_000L, LocalDate.of(2024, 5, 9)));

        // when
        MvcResult resultLte = mockMvc.perform(get("/api/v1/fruit/list")
                        .param("option", ComparisonOperator.LTE.name())
                        .param("price", "2500"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<FruitResponse> responseLte = objectMapper.readValue(resultLte.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<FruitResponse>>() {
        });

        assertThat(responseLte).extracting("name", "price", "warehousingDate")
                .contains(tuple("귤", 2_500L, LocalDate.of(2024, 4, 30)));
    }

    private void saveSoldFruit(String name, LocalDate warehousingDate, Long price) {
        Fruit fruit = new Fruit(name, warehousingDate, price);
        fruit.sold();
        fruitRepository.save(fruit);
    }

    private Fruit saveNotSoldFruit(String name, LocalDate warehousingDate, Long price) {
        Fruit fruit = new Fruit(name, warehousingDate, price);
        return fruitRepository.save(fruit);
    }
}
