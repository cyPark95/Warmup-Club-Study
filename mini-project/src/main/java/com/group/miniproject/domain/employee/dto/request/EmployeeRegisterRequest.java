package com.group.miniproject.domain.employee.dto.request;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeRegisterRequest(
        @NotBlank(message = "직원 이름은 null 또는 공백일 수 없습니다.")
        String name,
        @NotBlank(message = "팀 이름은 null 또는 공백일 수 없습니다.")
        String teamName,
        @NotNull(message = "직원의 매니저 여부는 null일 수 없습니다.")
        Boolean isManager,
        @NotNull(message = "직원의 입사 날짜는 null일 수 없습니다.")
        LocalDate joinDate,
        @NotNull(message = "직원의 생일은 null일 수 없습니다.")
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
