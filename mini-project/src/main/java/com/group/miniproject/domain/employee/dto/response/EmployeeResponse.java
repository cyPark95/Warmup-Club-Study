package com.group.miniproject.domain.employee.dto.response;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;

import java.time.LocalDate;

public record EmployeeResponse(
        String name,
        String teamName,
        EmployeeRole role,
        LocalDate birthday,
        LocalDate workStartDate
) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getName(),
                employee.getTeamName(),
                employee.getRole(),
                employee.getBirthday(),
                employee.getWorkStartDate()
        );
    }
}
