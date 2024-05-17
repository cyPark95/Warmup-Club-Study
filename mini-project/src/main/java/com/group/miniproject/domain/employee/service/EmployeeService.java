package com.group.miniproject.domain.employee.service;

import com.group.miniproject.domain.employee.dto.request.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.dto.response.EmployeeResponse;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.repository.EmployeeRepository;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.service.TeamService;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamService teamService;

    @Transactional
    public void saveEmployee(EmployeeRegisterRequest request) {
        log.debug("Request Data: {}", request);

        Team team = teamService.findTeamByName(request.teamName());
        log.debug("Find Team: {}", team);

        if (request.isManager() && team.hasManager()) {
            throw new ApiException(String.format("[%s] 팀에는 이미 매니저가 존재합니다.", request.teamName()), ExceptionCode.TEAM_ALREADY_HAS_MANAGER);
        }

        Employee employee = request.toEmployee();
        employee.joinTeam(team);

        log.debug("Save Employee: {}", employee);
        employeeRepository.save(employee);
    }

    public List<EmployeeResponse> findAllEmployee() {
        return employeeRepository.findAllFetchTeam().stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException(String.format("존재하지 않는 직원 ID[%d] 입니다.", id), ExceptionCode.NOT_FOUND_ENTITY));
    }
}
