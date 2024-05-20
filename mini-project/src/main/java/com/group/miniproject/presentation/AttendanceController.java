package com.group.miniproject.presentation;

import com.group.miniproject.domain.attendance.dto.request.AttendanceClockInRequest;
import com.group.miniproject.domain.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/clock-in")
    public void clockIn(@RequestBody @Valid AttendanceClockInRequest request) {
        attendanceService.recordClockIn(request);
    }
}
