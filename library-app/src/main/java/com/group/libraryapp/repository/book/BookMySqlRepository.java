package com.group.libraryapp.repository.book;

import org.springframework.stereotype.Repository;

@Repository
public class BookMySqlRepository implements BookRepository {

    @Override
    public void saveBook(String bookName) {
        System.out.println(bookName + "Save MySQL");
    }
}
