package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateReqeust;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserJpaService {

    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아래 있는 메서드가 시작될 때 START TRANSACTION;을 해준다.(트랙잭션 시작)
    // 메서드가 예외 없이 끝났다면 COMMIT
    // 혹시라도 예외가 발생한다면 ROLLBACK(IOException과 같은 Checked Exception은 롤백이 일어나지 않는다.)
    @Transactional
    public void saveUser(UserCreateReqeust request) {
        User user = new User(request.name(), request.age());
        userRepository.save(user);
    }

    // readOnly 속성을 사용하면 데이터 변경을 위한 불필요한 기능이 빠지기 때문에 성능적 이점이 있다.
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        // SELECT * FROM user WHERE id = ?;
        // Optional<User>
        User user = userRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new);

        // 변경 감지
        user.updateName(request.name());
    }

    @Transactional
    public void deleteUser(String name) {
        // SELECT * FROM user WHERE name = ?;
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
