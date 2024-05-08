package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateReqeust;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service: 분기 처리나 비즈니스 로직을 담당
@Service
public class UserJdbcService {

    private final UserJdbcRepository userJdbcRepository;

    public UserJdbcService(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public void saveUser(UserCreateReqeust request) {
        userJdbcRepository.saveUser(request.name(), request.age());
    }

    public List<UserResponse> getUsers() {
        return userJdbcRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        if (userJdbcRepository.isUserNotExist(request.id())) {
            // 예외 발생 시, Spring은 HTTP Status 500 Internal Server Error를 응답한다.
            throw new IllegalArgumentException();
        }

        userJdbcRepository.updateUserName(request.id(), request.name());
    }

    public void deleteUser(String name) {
        if (userJdbcRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }

        userJdbcRepository.deleteUser(name);
    }
}
