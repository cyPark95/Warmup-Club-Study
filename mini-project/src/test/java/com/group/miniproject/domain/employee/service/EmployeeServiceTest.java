package com.group.miniproject.domain.employee.service;

import com.group.miniproject.domain.employee.dto.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.employee.repository.EmployeeRepository;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.service.TeamService;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("직원 Service 검증")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TeamService teamService;

    @DisplayName("매니저 직원 등록 실패 - 이미 매니저 존재")
    @Test
    void saveEmployee_failHasManager() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee manager = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        manager.joinTeam(team);

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(true, team.getName());

        when(teamService.getTeamByName(team.getName())).thenReturn(team);

        // when
        // then
        assertThatThrownBy(() -> employeeService.saveEmployee(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매니저 직원 등록 성공 - 팀에 매니저 없음")
    @Test
    void saveEmployee_() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(false, team.getName());

        when(teamService.getTeamByName(team.getName())).thenReturn(team);

        // when
        employeeService.saveEmployee(request);

        // then
        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));
    }

    @DisplayName("멤버 직원 등록 성공")
    @Test
    void saveEmployee() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(false, team.getName());

        when(teamService.getTeamByName(team.getName())).thenReturn(team);

        // when
        employeeService.saveEmployee(request);

        // then
        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));

        // given
        Employee manager = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        manager.joinTeam(team);

        // when
        employeeService.saveEmployee(request);

        // then
        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));
    }
}
