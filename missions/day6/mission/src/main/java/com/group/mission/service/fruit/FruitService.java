package com.group.mission.service.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.request.FruitStatisticsRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public Fruit saveFruit(FruitSaveRequest request) {
        Fruit fruit = request.toDomain();
        return fruitRepository.save(fruit);
    }

    public void soldFruit(FruitSoldRequest request) {
        Fruit fruit = findById(request);
        fruit.sold();
        fruitRepository.save(fruit);
    }

    public FruitStatisticResponse finStatistic(FruitStatisticsRequest request) {
        return fruitRepository.findStatistic(request.name());
    }

    private Fruit findById(FruitSoldRequest request) {
        return fruitRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 ID[%d] 입니다.", request.id())));
    }
}
