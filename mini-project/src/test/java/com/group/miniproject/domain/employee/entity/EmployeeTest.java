package com.group.miniproject.domain.employee.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("직원 Domain 검증")
class EmployeeTest {

    @DisplayName("Employee 객체 생성 실패 - 유효하지 않은 Parameter")
    @ParameterizedTest
    @MethodSource("invalidConstructorParameter")
    void createEmployee(String name, EmployeeRole role, LocalDate joinDate, LocalDate birthday) {
        // when
        // then
        assertThatThrownBy(() -> new Employee(name, role, joinDate, birthday, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> invalidConstructorParameter() {
        return Stream.of(
                Arguments.of(null, EmployeeRole.MANAGER, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                Arguments.of("", EmployeeRole.MANAGER, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                Arguments.of("John Doe", null, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                Arguments.of("John Doe", EmployeeRole.MANAGER, null, LocalDate.of(1995, 10, 7)),
                Arguments.of("John Doe", EmployeeRole.MANAGER, LocalDate.now(), null)
        );
    }
}
