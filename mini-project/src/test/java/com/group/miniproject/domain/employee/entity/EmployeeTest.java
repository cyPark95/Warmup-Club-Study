package com.group.miniproject.domain.employee.entity;

import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.util.EmployeeFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("직원 Domain 검증")
class EmployeeTest {

    @DisplayName("Employee 객체 생성 시, 유효하지 않은 Parameter인 경우 예외 발생")
    @ParameterizedTest
    @MethodSource("invalidConstructorParameter")
    void createEmployee_invalidParameter(String name, EmployeeRole role, LocalDate joinDate, LocalDate birthday) {
        // when
        // then
        assertThatThrownBy(() -> new Employee(name, role, joinDate, birthday, null))
                .isInstanceOf(ApiException.class);
    }

    @DisplayName("매니저라면 True 반환")
    @Test
    void isManager() {
        // given
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);

        // when
        boolean result = employee.isManager();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("매니저가 아닌 경우 False 반환")
    @Test
    void isNotManager() {
        // given
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        // when
        boolean result = employee.isManager();

        // then
        assertThat(result).isFalse();
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
