package com.group.libraryapp.dto.book.request;

public record BookLoanRequest(
        String userName,
        String bookName
) {
}
