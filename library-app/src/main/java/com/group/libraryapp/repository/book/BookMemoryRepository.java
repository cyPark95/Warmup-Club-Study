package com.group.libraryapp.repository.book;

import java.util.ArrayList;
import java.util.List;

public class BookMemoryRepository implements BookRepository {

    private final List<String> books = new ArrayList<>();

    @Override
    public void saveBook(String bookName) {
        books.add(bookName);
    }
}
