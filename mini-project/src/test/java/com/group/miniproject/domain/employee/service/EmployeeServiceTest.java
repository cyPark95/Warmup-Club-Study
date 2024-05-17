package com.group.miniproject.domain.employee.service;

import com.group.miniproject.domain.employee.dto.request.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.dto.response.EmployeeResponse;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.employee.repository.EmployeeRepository;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.service.TeamService;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("멤버 직원 등록 성공")
    @Test
    void saveEmployee() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(false, team.getName());

        when(teamService.findTeamByName(team.getName())).thenReturn(team);

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

    @DisplayName("팀에 매니저가 없다면 매니저 직원 등록 성공")
    @Test
    void saveEmployee_() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(false, team.getName());

        when(teamService.findTeamByName(team.getName())).thenReturn(team);

        // when
        employeeService.saveEmployee(request);

        // then
        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));
    }

    @DisplayName("이미 매니저가 존재하는 팀에 매니저를 등록할 경우 예외 발생")
    @Test
    void saveEmployee_failHasManager() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee manager = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        manager.joinTeam(team);

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(true, team.getName());

        when(teamService.findTeamByName(team.getName())).thenReturn(team);

        // when
        // then
        assertThatThrownBy(() -> employeeService.saveEmployee(request))
                .isInstanceOf(ApiException.class);
    }

    @DisplayName("모든 직원 정보 조회 성공")
    @Test
    void findAllEmployee() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        employee.joinTeam(team);

        when(employeeRepository.findAllFetchTeam()).thenReturn(List.of(employee));

        // when
        List<EmployeeResponse> result = employeeService.findAllEmployee();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(employee.getName());
        assertThat(result.get(0).teamName()).isEqualTo(team.getName());
        assertThat(result.get(0).role()).isEqualTo(employee.getRole());
        assertThat(result.get(0).birthday()).isEqualTo(employee.getBirthday());
        assertThat(result.get(0).workStartDate()).isEqualTo(employee.getWorkStartDate());
    }

    @DisplayName("직원 ID로 조회 성공")
    @Test
    void getEmployee() {
        // given
        Long employeeId = -1L;
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // when
        Employee result = employeeService.getEmployee(employeeId);

        // then
        assertThat(result).isEqualTo(employee);
    }

    @DisplayName("없는 직원의 ID로 조회 시, 예외가 발생")
    @Test
    void findTeamByName_notFoundName() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> employeeService.getEmployee(-1L))
                .isInstanceOf(ApiException.class);
    }
}
