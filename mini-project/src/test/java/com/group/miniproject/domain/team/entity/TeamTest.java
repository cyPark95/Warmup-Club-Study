package com.group.miniproject.domain.team.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("팀 Domain 검증")
class TeamTest {

    @DisplayName("Team 객체 생성 실패 - 유효하지 Parameter")
    @ParameterizedTest
    @NullAndEmptySource
    void createTeam(String name) {
        // when
        // then
        assertThatThrownBy(() -> new Team(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
