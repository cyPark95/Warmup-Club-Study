package com.group.miniproject.domain.employee.controller;

import com.group.miniproject.domain.employee.dto.request.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.dto.response.EmployeeResponse;
import com.group.miniproject.domain.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public void registerEmployee(@RequestBody @Valid EmployeeRegisterRequest request) {
        employeeService.saveEmployee(request);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployee() {
        return employeeService.findAllEmployee();
    }
}
