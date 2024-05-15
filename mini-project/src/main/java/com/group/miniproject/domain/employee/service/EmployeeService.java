package com.group.miniproject.domain.employee.service;

import com.group.miniproject.domain.employee.dto.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.repository.EmployeeRepository;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamService teamService;

    @Transactional
    public void saveEmployee(EmployeeRegisterRequest request) {
        Team team = teamService.getTeamByName(request.teamName());

        if (request.isManager() && team.hasManager()) {
            throw new IllegalArgumentException(String.format("[%s] 팀에는 이미 매니저가 존재합니다.", request.teamName()));
        }

        Employee employee = request.toEmployee();
        employee.joinTeam(team);
        employeeRepository.save(employee);
    }
}
