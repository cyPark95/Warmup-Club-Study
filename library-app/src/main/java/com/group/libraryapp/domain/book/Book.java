package com.group.libraryapp.domain.book;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Book() {
    }

    public Book(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }

        this.name = name;
    }
}
