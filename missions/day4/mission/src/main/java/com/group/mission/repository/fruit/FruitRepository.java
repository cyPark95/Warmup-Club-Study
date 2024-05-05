package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;

import java.util.Optional;

public interface FruitRepository {

    Fruit save(Fruit fruit);

    Optional<Fruit> findById(Long id);

    FruitStatisticResponse findStatistic(String name);
}
