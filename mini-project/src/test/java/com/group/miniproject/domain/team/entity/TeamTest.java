package com.group.miniproject.domain.team.entity;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("팀 Domain 검증")
class TeamTest {

    @DisplayName("Team 객체 생성 실패 - 유효하지 Parameter")
    @ParameterizedTest
    @NullAndEmptySource
    void createTeam_invalidParameter(String name) {
        // when
        // then
        assertThatThrownBy(() -> new Team(name))
                .isInstanceOf(ApiException.class);
    }

    @DisplayName("매니저 존재 검증 - 존재하지 않는 경우")
    @Test
    void hasNotManage() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        Employee firstEmployee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        Employee secondEmployee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        team.addEmployee(firstEmployee);
        team.addEmployee(secondEmployee);

        // when
        boolean result = team.hasManager();

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("매니저 존재 검증 - 존재하는 경우")
    @Test
    void hasManage() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        Employee firstEmployee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        Employee secondEmployee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        team.addEmployee(firstEmployee);
        team.addEmployee(secondEmployee);

        // when
        boolean result = team.hasManager();

        // then
        assertThat(result).isTrue();
    }
}
