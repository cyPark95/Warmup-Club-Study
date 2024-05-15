package com.group.miniproject.domain.employee.dto;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;

import java.time.LocalDate;

public record EmployeeRegisterRequest(
        String name,
        String teamName,
        Boolean isManager,
        LocalDate joinDate,
        LocalDate birthday
) {

    public Employee toEmployee() {
        return Employee.builder()
                .name(this.name)
                .role(this.isManager ? EmployeeRole.MANAGER : EmployeeRole.MEMBER)
                .joinDate(this.joinDate)
                .birthday(this.birthday)
                .build();
    }
}
