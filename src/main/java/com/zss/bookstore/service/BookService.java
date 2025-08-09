package com.zss.bookstore.service;

import com.zss.bookstore.domain.Book;
import com.zss.bookstore.dto.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:13
 * projectName     zss-bookstore
 **/

public interface BookService {
    Book createBook(BookRequest bookRequest);
    Page<Book> getBooksByCategory(Long categoryId, Pageable pageable);
    Book getBookById(Long bookId);
    Page<Book> getAllBooks(Pageable pageable);
}
