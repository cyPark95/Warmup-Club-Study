package com.group.mission.controller.fruit;

import com.group.mission.dto.fruit.request.*;
import com.group.mission.dto.fruit.response.FruitCountResponse;
import com.group.mission.dto.fruit.response.FruitResponse;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.service.fruit.FruitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public void saveFruit(@RequestBody @Valid FruitSaveRequest request) {
        fruitService.saveFruit(request);
    }

    @PutMapping
    public void soldFruit(@RequestBody @Valid FruitSoldRequest request) {
        fruitService.soldFruit(request);
    }

    @GetMapping("/stat")
    public FruitStatisticResponse getStatistic(@Valid FruitStatisticsRequest request) {
        return fruitService.findStatistic(request);
    }

    @GetMapping("/count")
    public FruitCountResponse getFruitCount(@Valid FruitCountRequest request) {
        return fruitService.findFruitCount(request);
    }

    @GetMapping("/list")
    public List<FruitResponse> getFruits(@Valid FruitRequest request) {
        return fruitService.findRegisteredFruits(request);
    }
}
