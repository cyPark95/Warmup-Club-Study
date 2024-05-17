package com.group.miniproject.domain.attendance.entity;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.global.exception.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("출퇴근 Domain 검증")
class AttendanceTest {


    @DisplayName("Attendance 객체 생성 시, 유효하지 않은 Parameter인 경우 예외 발생")
    @ParameterizedTest
    @NullSource
    void createAttendance_invalidParameter(Employee employee) {
        // when
        // then
        assertThatThrownBy(() -> new Attendance(employee))
                .isInstanceOf(ApiException.class);
    }
}
