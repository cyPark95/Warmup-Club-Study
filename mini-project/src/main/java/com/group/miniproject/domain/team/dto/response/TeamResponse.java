package com.group.miniproject.domain.team.dto.response;

import com.group.miniproject.domain.team.entity.Team;

public record TeamResponse(
        String name,
        String manager,
        long memberCount
) {

    public static TeamResponse from(Team team) {
        return new TeamResponse(
                team.getName(),
                team.getManagerName(),
                team.getMemberCount()
        );
    }
}
