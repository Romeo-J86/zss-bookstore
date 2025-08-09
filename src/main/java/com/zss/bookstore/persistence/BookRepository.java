package com.zss.bookstore.persistence;

import com.zss.bookstore.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:05
 * projectName     zss-bookstore
 **/

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByCategoryId(Long categoryId,
                                Pageable pageable);
}
