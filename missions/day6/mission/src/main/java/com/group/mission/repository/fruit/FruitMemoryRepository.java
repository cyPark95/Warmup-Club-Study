package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    private long sequence = 1L;
    private final static Map<Long, Fruit> FRUIT_STORE = new ConcurrentHashMap<>();

    @Override
    public Fruit save(Fruit fruit) {
        Fruit newFruit = new Fruit(
                sequence,
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.getStatus()
        );

        FRUIT_STORE.put(sequence++, newFruit);
        return newFruit;
    }

    @Override
    public Optional<Fruit> findById(Long id) {
        return Optional.ofNullable(FRUIT_STORE.get(id));
    }

    @Override
    public FruitStatisticResponse findStatistic(String name) {
        Map<FruitStatus, Long> fruitStatusMap = calculateFruitStatus(name);
        return new FruitStatisticResponse(
                fruitStatusMap.getOrDefault(FruitStatus.SOLD, 0L),
                fruitStatusMap.getOrDefault(FruitStatus.REGISTERED, 0L)
        );
    }

    private static Map<FruitStatus, Long> calculateFruitStatus(String name) {
        return FRUIT_STORE.values().stream()
                .filter(fruit -> fruit.getName().equals(name))
                .collect(Collectors.toMap(
                        Fruit::getStatus,
                        Fruit::getPrice,
                        Long::sum
                ));
    }
}
