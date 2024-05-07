package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository: DB와의 접근을 담당
@Repository
public class UserRepository {

    private static final String TABLE = "user";

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(Long id) {
        String readSql = String.format("SELECT * FROM %s WHERE id = ?", TABLE);
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(Long id, String name) {
        String sql = String.format("UPDATE %s SET name = ? WHERE id = ?", TABLE);
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name) {
        String readSql = String.format("SELECT * FROM %s WHERE name = ?", TABLE);
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
    }

    public void deleteUser(String name) {
        String sql = String.format("DELETE FROM %s WHERE name = ?", TABLE);
        jdbcTemplate.update(sql, name);
    }

    public void saveUser(String name, Integer age) {
        // 값이 들어가는 부분에 ?를 사용하면 동적으로 값을 넣어줄 수 있다.
        String sql = String.format("INSERT INTO %s (name, age) VALUE (?, ?);", TABLE);
        // JdbcTemplate의 update() 메서드를 사용하면 INSERT, UPDATE, DELETE 쿼리를 사용할 수 있다.
        jdbcTemplate.update(sql, name, age);
    }

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
}
