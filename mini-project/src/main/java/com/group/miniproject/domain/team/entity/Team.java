package com.group.miniproject.domain.team.entity;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.global.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "team")
    private final List<Employee> employees = new ArrayList<>();

    @Builder
    public Team(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(String.format("유효하지 않은 팀 이름[%s] 입니다.", name));
        }

        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
}
