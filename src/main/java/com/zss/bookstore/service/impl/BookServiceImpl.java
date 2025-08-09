package com.zss.bookstore.service.impl;

import com.zss.bookstore.convertor.BookObjectMapper;
import com.zss.bookstore.domain.Book;
import com.zss.bookstore.dto.request.BookRequest;
import com.zss.bookstore.exception.BookNotFoundException;
import com.zss.bookstore.exception.CategoryNotFoundException;
import com.zss.bookstore.persistence.BookRepository;
import com.zss.bookstore.persistence.CategoryRepository;
import com.zss.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:14
 * projectName     zss-bookstore
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookObjectMapper bookObjectMapper;

    @Override
    public Book createBook(BookRequest bookRequest) {
        requireNonNull(bookRequest, "bookRequest must not be null");

        var category = categoryRepository
                .findById(bookRequest.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                format("Category with id %d not found",
                                        bookRequest.getCategoryId())
                        ));
        log.info("Category found: {}", category);
        Book book = bookObjectMapper.toBook(bookRequest);
        book.setCategory(category);
        log.info("Book created: {}", book);

        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getBooksByCategory(Long categoryId, Pageable pageable) {
        requireNonNull(categoryId, "categoryId must not be null");
        log.info("Getting books for category id: {}", categoryId);
        return bookRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Book getBookById(Long bookId) {
        requireNonNull(bookId, "bookId must not be null");
        log.info("Getting book with id: {}", bookId);
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(
                        format("Book with id %d not found", bookId)
                ));
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        log.info("Getting all books");
        return bookRepository.findAll(pageable);
    }
}