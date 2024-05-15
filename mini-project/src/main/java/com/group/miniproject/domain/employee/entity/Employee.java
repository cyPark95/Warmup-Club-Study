package com.group.miniproject.domain.employee.entity;

import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.global.entity.BaseDateTimeEntity;
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
    private LocalDate joinDate;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Employee(String name, EmployeeRole role, LocalDate joinDate, LocalDate birthday, Team team) {
        parameterValidation(name, role, joinDate, birthday);

        this.name = name;
        this.role = role;
        this.joinDate = joinDate;
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
            throw new IllegalArgumentException(String.format("유효하지 않은 직원 이름[%s] 입니다.", name));
        }

        if(role == null) {
            throw new IllegalArgumentException("역할은 필수 값 입니다.");
        }

        if(joinDate == null) {
            throw new IllegalArgumentException("회사에 들어온 날짜는 필수 값 입니다.");
        }

        if(birthday == null) {
            throw new IllegalArgumentException("생일은 필수 값 입니다.");
        }
    }
}
