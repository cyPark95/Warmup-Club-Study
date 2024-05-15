package com.group.miniproject.domain.team.entity;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.global.entity.BaseDateTimeEntity;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "employees")
@Entity
public class Team extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private final List<Employee> employees = new ArrayList<>();

    @Builder
    public Team(String name) {
        if (!StringUtils.hasText(name)) {
            throw new ApiException(String.format("유효하지 않은 팀 이름[%s] 입니다.", name), ExceptionCode.TEAM_NAME_INVALID);
        }

        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public boolean hasManager() {
        return employees.stream()
                .anyMatch(Employee::isManager);
    }
}
