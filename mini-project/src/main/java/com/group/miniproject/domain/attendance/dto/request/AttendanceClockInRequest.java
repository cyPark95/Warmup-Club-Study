package com.group.miniproject.domain.attendance.dto.request;

import jakarta.validation.constraints.NotNull;

public record AttendanceClockInRequest(
        @NotNull(message = "직원 ID는 null일 수 없습니다.")
        Long employeeId
) {
}
