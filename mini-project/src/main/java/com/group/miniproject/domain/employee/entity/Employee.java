package com.group.miniproject.domain.employee.entity;

import com.group.miniproject.global.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Employee(String name, EmployeeRole role, LocalDate joinDate, LocalDate birthday) {
        parameterValidation(name, role, joinDate, birthday);

        this.name = name;
        this.role = role;
        this.joinDate = joinDate;
        this.birthday = birthday;
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
