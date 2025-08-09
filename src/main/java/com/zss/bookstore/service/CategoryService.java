package com.zss.bookstore.service;

import com.zss.bookstore.domain.Category;
import com.zss.bookstore.dto.request.CategoryRequest;

import java.util.List;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:32
 * projectName     zss-bookstore
 **/

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategories();
}
