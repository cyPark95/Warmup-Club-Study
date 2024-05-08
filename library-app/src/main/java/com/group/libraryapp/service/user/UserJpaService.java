package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateReqeust;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJpaService {

    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateReqeust request) {
        User user = new User(request.name(), request.age());
        userRepository.save(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    public void updateUser(UserUpdateRequest request) {
        // SELECT * FROM user WHERE id = ?;
        // Optional<User>
        User user = userRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.name());
        userRepository.save(user);
    }

    public void deleteUser(String name) {
        // SELECT * FROM user WHERE name = ?;
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
