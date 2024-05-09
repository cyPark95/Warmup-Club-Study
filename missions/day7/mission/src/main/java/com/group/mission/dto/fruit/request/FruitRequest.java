package com.group.mission.dto.fruit.request;

import com.group.mission.common.ComparisonOperator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FruitRequest(
        @NotNull
        ComparisonOperator option,
        @Min(0)
        long price
) {
}
