package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    // Primary Key로 간주한다.
    @Id
    // @GeneratedValue: Primary Key 자동 생성 전략을 지정한다.
    // GenerationType.IDENTITY: Primary Key를 자동으로 생성한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB 컬럼에 대한 설정
    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    // 1: N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }

        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        targetHistory.doReturn();
    }
}
