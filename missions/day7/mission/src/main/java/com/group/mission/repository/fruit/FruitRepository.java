package com.group.mission.repository.fruit;

import com.group.mission.domain.fruit.Fruit;
import com.group.mission.domain.fruit.FruitStatus;
import com.group.mission.dto.fruit.response.FruitCountResponse;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    @Query("SELECT new com.group.mission.dto.fruit.response.FruitStatisticResponse(" +
            "SUM(CASE WHEN f.status = 'SOLD' THEN f.price END), " +
            "SUM(CASE WHEN f.status = 'REGISTERED' THEN f.price END)" +
            ") " +
            "FROM Fruit f " +
            "WHERE f.name = :name")
    FruitStatisticResponse findStatistic(String name);

    FruitCountResponse countByNameAndStatus(String name, FruitStatus status);

    List<Fruit> findAllByPriceGreaterThanEqualAndStatus(long price, FruitStatus status);

    List<Fruit> findAllByPriceLessThanEqualAndStatus(long price, FruitStatus status);
}
