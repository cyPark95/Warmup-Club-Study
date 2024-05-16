package com.group.miniproject.domain.employee.entity;

import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.global.entity.BaseDateTimeEntity;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "team")
@Entity
public class Employee extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmployeeRole role;

    @Column(nullable = false)
    private LocalDate workStartDate;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Employee(String name, EmployeeRole role, LocalDate workStartDate, LocalDate birthday, Team team) {
        parameterValidation(name, role, workStartDate, birthday);

        this.name = name;
        this.role = role;
        this.workStartDate = workStartDate;
        this.birthday = birthday;
        this.team = team;
    }

    public void joinTeam(Team team) {
        if(this.team != null) {
            this.team.getEmployees().remove(this);
        }

        this.team = team;
        this.team.addEmployee(this);
    }

    public boolean isManager() {
        return this.role == EmployeeRole.MANAGER;
    }

    private void parameterValidation(String name, EmployeeRole role, LocalDate joinDate, LocalDate birthday) {
        if (!StringUtils.hasText(name)) {
            throw new ApiException(String.format("유효하지 않은 직원 이름[%s] 입니다.", name), ExceptionCode.EMPLOYEE_NAME_INVALID);
        }

        if(role == null) {
            throw new ApiException(ExceptionCode.EMPLOYEE_ROLE_NULL);
        }

        if(joinDate == null) {
            throw new ApiException(ExceptionCode.EMPLOYEE_WORK_START_DATE_NULL);
        }

        if(birthday == null) {
            throw new ApiException(ExceptionCode.EMPLOYEE_BIRTHDAY_NULL);
        }
    }
}
