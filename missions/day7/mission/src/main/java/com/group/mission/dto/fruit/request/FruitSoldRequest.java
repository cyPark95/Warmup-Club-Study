package com.group.mission.dto.fruit.request;

import jakarta.validation.constraints.NotNull;

public record FruitSoldRequest(
        @NotNull
        Long id
) {
}
