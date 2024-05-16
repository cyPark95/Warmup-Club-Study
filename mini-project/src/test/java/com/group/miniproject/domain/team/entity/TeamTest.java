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

    @DisplayName("Team 객체 생성 시, 유효하지 않은 Parameter인 경우 예외 발생")
    @ParameterizedTest
    @NullAndEmptySource
    void createTeam_invalidParameter(String name) {
        // when
        // then
        assertThatThrownBy(() -> new Team(name))
                .isInstanceOf(ApiException.class);
    }

    @DisplayName("매니저가 존재하면 True 반환")
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

    @DisplayName("매니저가 존재하지 않으면 False 반환")
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

    @DisplayName("팀에 매니저 이름 조회")
    @Test
    void getManagerName() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        team.addEmployee(employee);

        // when
        String result = team.getManagerName();

        // then
        assertThat(result).isEqualTo(employee.getName());
    }

    @DisplayName("팀에 매니저가 없는 경우 null 반환")
    @Test
    void getManagerName_isNull() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        team.addEmployee(employee);

        // when
        String result = team.getManagerName();

        // then
        assertThat(result).isNull();
    }
}
