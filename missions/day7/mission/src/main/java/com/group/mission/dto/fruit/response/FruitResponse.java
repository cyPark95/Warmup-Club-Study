package com.group.mission.dto.fruit.response;

import com.group.mission.domain.fruit.Fruit;

import java.time.LocalDate;

public record FruitResponse(
        String name,
        Long price,
        LocalDate warehousingDate
) {

    public static FruitResponse from(Fruit fruit) {
        return new FruitResponse(fruit.getName(), fruit.getPrice(), fruit.getWarehousingDate());
    }
}
