package com.group.mission.dto.fruit.request;

import com.group.mission.domain.Fruit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

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
