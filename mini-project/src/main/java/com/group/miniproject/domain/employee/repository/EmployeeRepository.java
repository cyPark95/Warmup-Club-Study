package com.group.miniproject.domain.employee.repository;

import com.group.miniproject.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e LEFT JOIN e.team")
    List<Employee> findAllFetchTeam();
}
