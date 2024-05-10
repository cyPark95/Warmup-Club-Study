package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    // Memory -> MySQL로 저장하도록 변경한다면
    // 어디에 저장할지는 Repository의 역할이다.
    // 하지만 BookService에서도 코드의 수정이 필요하다.
//    private final BookMemoryRepository bookRepository = new BookMemoryRepository();
//    private final BookMySqlRepository bookRepository = new BookMySqlRepository();

    // 인터페이스 사용
    // 인스턴화를 위한 생성자 코드만 수정하면 된다.
    // 하지만 BookService에서 여전히 코드의 수정이 필요하다.
//    private final BookRepository bookRepository = new BookMemoryRepository();
//    private final BookRepository bookRepository = new BookMySqlRepository();

    // 스프링 컨테이너 사용
    // 스프링 컨테이너가 BookMemoryRepository와 BookMySqlRepository 중 자동으로 선택해서 BookService를 만들어준다.
    // 이런 방식을 제어의 역전(IoC, Inversion of Control)이라고 한다.
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        Book book = new Book(request.name());
        bookRepository.save(book);
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        // 1. 책 정보 조회
        Book book = bookRepository.findByName(request.bookName())
                .orElseThrow(IllegalArgumentException::new);

        // 2. 대출기록 정보를 확인해서 대출 중인지 확인
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            // 3. 만약 대출 중이라면 예외 발생
            throw new IllegalArgumentException("이미 대출 중인 책 입니다.");
        }

        // 4. 유저 정보 조회
        User user = userRepository.findByName(request.userName())
                .orElseThrow(IllegalArgumentException::new);

        // 5. 유저 정보와 책 정보 를 기반으로 대출기록 정보 생성
        UserLoanHistory history = new UserLoanHistory(user.getId(), book.getName());

        // 6. 대출기록 정보 저장
        userLoanHistoryRepository.save(history);
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository.findByName(request.userName())
                .orElseThrow(IllegalArgumentException::new);

        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.bookName())
                .orElseThrow(IllegalArgumentException::new);
        history.doReturn();
    }
}
