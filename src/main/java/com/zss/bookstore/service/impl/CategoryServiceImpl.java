package com.zss.bookstore.service.impl;

import com.zss.bookstore.convertor.CategoryObjectMapper;
import com.zss.bookstore.domain.Category;
import com.zss.bookstore.dto.request.CategoryRequest;
import com.zss.bookstore.exception.CategoryNotFoundException;
import com.zss.bookstore.persistence.CategoryRepository;
import com.zss.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:36
 * projectName     zss-bookstore
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryObjectMapper categoryObjectMapper;

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        requireNonNull(categoryRequest, "categoryRequest must not be null");
        Category category = categoryObjectMapper.toCategory(categoryRequest);
        categoryRepository.save(category);
        log.info("Category created: {}", category);
        return category;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        requireNonNull(categoryId, "categoryId must not be null");
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        format("Category with id %d not found", categoryId)
                ));
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Getting all categories");
        return categoryRepository.findAll();
    }
}
