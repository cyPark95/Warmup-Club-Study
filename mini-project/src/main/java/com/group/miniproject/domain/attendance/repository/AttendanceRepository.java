package com.group.miniproject.domain.attendance.repository;

import com.group.miniproject.domain.attendance.entity.Attendance;
import com.group.miniproject.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeAndClockInBetween(Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
