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

    /**
     * 과일 정보 저장
     */
    public Fruit saveFruit(FruitSaveRequest request) {
        Fruit fruit = request.toDomain();
        return fruitRepository.save(fruit);
    }

    /**
     * 과일 판매 정보 기록
     */
    public void soldFruit(FruitSoldRequest request) {
        Fruit fruit = fruitRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 ID[%d] 입니다.", request.id())));
        fruit.sold();
        fruitRepository.save(fruit);
    }

    public FruitStatisticResponse finStatistic(FruitStatisticsRequest request) {
        return fruitRepository.findStatistic(request.name());
    }
}
