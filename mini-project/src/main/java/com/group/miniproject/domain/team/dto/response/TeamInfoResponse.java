package com.group.miniproject.domain.team.dto.response;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.team.entity.Team;

public record TeamInfoResponse(
        String name,
        String manager,
        long memberCount
) {

    public static TeamInfoResponse from(Team team) {
        return new TeamInfoResponse(
                team.getName(),
                team.getManager()
                        .map(Employee::getName)
                        .orElse(null),
                team.getMemberCount()
        );
    }
}
