package com.group.libraryapp.domain.user.loanhistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String bookName;
    private boolean isReturn;

    protected UserLoanHistory() {
    }

    public UserLoanHistory(Long userId, String bookName) {
        this.userId = userId;
        this.bookName = bookName;
    }

    public void doReturn() {
        this.isReturn = true;
    }
}
