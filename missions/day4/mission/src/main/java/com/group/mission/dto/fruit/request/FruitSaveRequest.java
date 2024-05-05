package com.group.mission.dto.fruit.request;

import com.group.mission.domain.Fruit;

import java.time.LocalDate;

public record FruitSaveRequest(
        String name,
        LocalDate warehousingDate,
        long price
) {

    public Fruit toDomain() {
        return new Fruit(name, warehousingDate, price);
    }
}
