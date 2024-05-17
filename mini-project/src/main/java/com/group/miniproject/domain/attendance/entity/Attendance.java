package com.group.miniproject.domain.attendance.entity;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.global.entity.BaseDateTimeEntity;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Attendance extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Builder
    public Attendance(Employee employee) {
        parameterValidation(employee);

        this.employee = employee;
    }

    public void clockIn() {
        this.clockIn = LocalDateTime.now();
    }

    private void parameterValidation(Employee employee) {
        if(employee == null) {
            throw new ApiException(ExceptionCode.EMPLOYEE_IS_NULL);
        }
    }
}
