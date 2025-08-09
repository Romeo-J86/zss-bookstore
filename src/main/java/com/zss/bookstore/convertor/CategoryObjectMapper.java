package com.zss.bookstore.convertor;

import com.zss.bookstore.domain.Category;
import com.zss.bookstore.dto.request.CategoryRequest;
import com.zss.bookstore.dto.response.CategoryResponse;
import org.mapstruct.Mapper;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:54
 * projectName     zss-bookstore
 **/

@Mapper(componentModel = "spring")
public interface CategoryObjectMapper {

    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Category category);
}
