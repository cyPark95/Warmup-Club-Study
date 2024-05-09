package com.group.mission.service.fruit;

import com.group.mission.common.ComparisonOperator;
import com.group.mission.domain.fruit.Fruit;
import com.group.mission.domain.fruit.FruitStatus;
import com.group.mission.dto.fruit.request.*;
import com.group.mission.dto.fruit.response.FruitCountResponse;
import com.group.mission.dto.fruit.response.FruitResponse;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Transactional
    public void saveFruit(FruitSaveRequest request) {
        Fruit fruit = request.toDomain();
        fruitRepository.save(fruit);
    }

    @Transactional
    public void soldFruit(FruitSoldRequest request) {
        Fruit fruit = findById(request);
        fruit.sold();
    }

    public FruitStatisticResponse findStatistic(FruitStatisticsRequest request) {
        return fruitRepository.findStatistic(request.name());
    }

    public FruitCountResponse findFruitCount(FruitCountRequest request) {
        return fruitRepository.countByNameAndStatus(request.name(), FruitStatus.SOLD);
    }

    public List<FruitResponse> findRegisteredFruits(FruitRequest request) {
        List<Fruit> fruits = findFruitsByComparisonOperator(request, FruitStatus.REGISTERED);
        return fruits.stream()
                .map(FruitResponse::from)
                .toList();
    }

    private Fruit findById(FruitSoldRequest request) {
        return fruitRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 ID[%d] 입니다.", request.id())));
    }

    private List<Fruit> findFruitsByComparisonOperator(FruitRequest request, FruitStatus status) {
        if (request.option() == ComparisonOperator.LTE) {
            return fruitRepository.findAllByPriceLessThanEqualAndStatus(request.price(), status);
        }
        return fruitRepository.findAllByPriceGreaterThanEqualAndStatus(request.price(), status);
    }
}
