package com.group.libraryapp.dto.user.request;

public record UserCreateReqeust(

        String name,
        Integer age  // 나이는 필수 입력이 아니기 때문에 Boxing 클래스 사용
) {
}
