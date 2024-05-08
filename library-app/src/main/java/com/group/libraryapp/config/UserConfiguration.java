package com.group.libraryapp.config;

import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

// @Configuration 어노테이션은 일반적으로 config 패키지에서 관리한다.
// @Configuration 어노테이션과 @Bean 어노테이션을 사용하면 스프링 빈으로 등록할 수 있다.
@Configuration
public class UserConfiguration {

    @Bean
    public UserJdbcRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserJdbcRepository(jdbcTemplate);
    }
}
