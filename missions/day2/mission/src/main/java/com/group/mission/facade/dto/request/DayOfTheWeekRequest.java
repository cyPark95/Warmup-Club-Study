package com.group.mission.facade.dto.request;

import java.time.LocalDate;

public record DayOfTheWeekRequest(
        LocalDate date
) {
}
