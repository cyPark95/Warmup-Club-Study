package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateReqeust;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController 어노테이션을 사용하면 Body에 데이터를 포함하여 응답을 전송할 수 있다.
@RestController
public class UserController {

    private static final String TABLE = "user";

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user")  // POST /user
    public void saveUser(@RequestBody UserCreateReqeust request) {
        // 값이 들어가는 부분에 ?를 사용하면 동적으로 값을 넣어줄 수 있다.
        String sql = String.format("INSERT INTO %s (name, age) VALUE (?, ?);", TABLE);
        // JdbcTemplate의 update() 메서드를 사용하면 INSERT, UPDATE, DELETE 쿼리를 사용할 수 있다.
        jdbcTemplate.update(sql, request.name(), request.age());
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = String.format("SELECT * FROM %s", TABLE);
        // JdbcTemplate의 query() 메서드를 사용하면 SELECT 쿼리를 사용할 수 있다.
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");

            return new UserResponse(id, name, age);
        });
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        String sql = String.format("UPDATE %s SET name = ? WHERE id = ?", TABLE);
        jdbcTemplate.update(sql, request.name(), request.id());
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam("name") String name) {
        String sql = String.format("DELETE FROM %s WHERE name = ?", TABLE);
        jdbcTemplate.update(sql, name);
    }
}
