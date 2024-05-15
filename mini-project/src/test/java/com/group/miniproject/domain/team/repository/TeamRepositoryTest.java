package com.group.miniproject.domain.team.repository;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("팀 Repository 검증")
@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @DisplayName("팀과 직원 함께 조회 - 팀원이 없는 경우")
    @Test
    void findAllFetchEmployee_emptyEmployee() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        teamRepository.save(team);

        // when
        List<Team> result = teamRepository.findAllFetchEmployee();

        // then
        assertThat(result.get(0)).isEqualTo(team);
        assertThat(result.get(0).getEmployees().isEmpty()).isTrue();
    }

    @DisplayName("팀과 직원 함께 조회 - 팀원이 있는 경우")
    @Test
    void findAllFetchEmployee() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        employee.joinTeam(team);

        teamRepository.save(team);

        // when
        List<Team> result = teamRepository.findAllFetchEmployee();

        // then
        assertThat(result.get(0)).isEqualTo(team);
        assertThat(result.get(0).getEmployees().get(0)).isEqualTo(employee);
    }
}
