package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.domain.User;

// HTTP 결과를 JSON으로 반환하기 위해서는 Getter가 필요하다.
public record UserResponse(
        long id,  // // 데이터의 식별 값
        String name,
        Integer age
) {

    public static UserResponse from(long id, User user) {
        return new UserResponse(id, user.getName(), user.getAge());
    }
}
