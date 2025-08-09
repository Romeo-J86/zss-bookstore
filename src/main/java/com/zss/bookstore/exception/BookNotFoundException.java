package com.zss.bookstore.exception;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     12:18
 * projectName     zss-bookstore
 **/

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
