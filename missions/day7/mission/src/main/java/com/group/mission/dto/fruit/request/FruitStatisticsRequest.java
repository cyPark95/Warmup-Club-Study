package com.group.mission.dto.fruit.request;

import jakarta.validation.constraints.NotEmpty;

public record FruitStatisticsRequest(
        @NotEmpty
        String name
) {
}
