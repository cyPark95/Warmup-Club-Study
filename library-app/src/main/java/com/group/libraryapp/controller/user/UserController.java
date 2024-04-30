package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.User;
import com.group.libraryapp.dto.user.request.UserCreateReqeust;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// @RestController 어노테이션을 사용하면 Body에 데이터를 포함하여 응답을 전송할 수 있다.
@RestController
public class UserController {

    private final List<User> users = new ArrayList<>();

    @PostMapping("/user")  // POST /user
    public void saveUser(@RequestBody UserCreateReqeust request) {
        users.add(new User(request.name(), request.age()));
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return IntStream.range(0, users.size())
                .mapToObj(i -> UserResponse.from(i + 1, users.get(i)))
                .toList();
    }
}
