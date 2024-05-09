package com.group.mission.dto.fruit.request;

import com.group.mission.domain.fruit.Fruit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FruitSaveRequest(
        @NotEmpty
        String name,
        @NotNull
        LocalDate warehousingDate,
        @Min(0)
        long price
) {

    public Fruit toDomain() {
        return new Fruit(name, warehousingDate, price);
    }
}
