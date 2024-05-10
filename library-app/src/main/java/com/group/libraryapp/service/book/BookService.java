package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.domain.book.BookRepository;
import org.springframework.stereotype.Service;

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

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        Book book = new Book(request.name());
        bookRepository.save(book);
    }
}
