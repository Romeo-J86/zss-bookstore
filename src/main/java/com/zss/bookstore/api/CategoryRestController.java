package com.zss.bookstore.api;

import com.zss.bookstore.convertor.CategoryObjectMapper;
import com.zss.bookstore.dto.request.CategoryRequest;
import com.zss.bookstore.dto.response.CategoryResponse;
import com.zss.bookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     12:04
 * projectName     zss-bookstore
 **/

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "REST APIs for managing book categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final CategoryObjectMapper categoryObjectMapper;

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(CREATED)
                .body(categoryObjectMapper.toCategoryResponse(
                        categoryService.createCategory(categoryRequest)));
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
                        .stream()
                        .map(categoryObjectMapper::toCategoryResponse)
                        .toList()
        );
    }

    @Operation(summary = "Get a category by its ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID of the category", example = "1")
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(
                categoryObjectMapper.toCategoryResponse(
                        categoryService.getCategoryById(categoryId)));
    }
}
