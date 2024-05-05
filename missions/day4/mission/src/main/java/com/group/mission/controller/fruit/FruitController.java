package com.group.mission.controller.fruit;

import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.request.FruitStatisticsRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.service.fruit.FruitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public void saveFruit(@RequestBody FruitSaveRequest request) {
        fruitService.saveFruit(request);
    }

    @PutMapping
    public void soldFruit(@RequestBody FruitSoldRequest request) {
        fruitService.soldFruit(request);
    }

    @GetMapping("/stat")
    public FruitStatisticResponse getStatistic(FruitStatisticsRequest request) {
        return fruitService.finStatistic(request);
    }
}
