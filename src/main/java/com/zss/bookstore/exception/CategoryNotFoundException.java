package com.zss.bookstore.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:17
 * projectName     zss-bookstore
 **/

@ResponseStatus(NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
