package com.group.miniproject.domain.employee.repository;

import com.group.miniproject.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
