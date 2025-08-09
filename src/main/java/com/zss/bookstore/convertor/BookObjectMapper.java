package com.zss.bookstore.convertor;

import com.zss.bookstore.domain.Book;
import com.zss.bookstore.dto.request.BookRequest;
import com.zss.bookstore.dto.response.BookResponse;
import org.mapstruct.Mapper;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:52
 * projectName     zss-bookstore
 **/

@Mapper(componentModel = "spring")
public interface BookObjectMapper {

    Book toBook(BookRequest bookRequest);

    BookResponse toBookResponse(Book book);
}
