package com.zss.bookstore.service.impl;

import com.zss.bookstore.convertor.CategoryObjectMapper;
import com.zss.bookstore.domain.Category;
import com.zss.bookstore.dto.request.CategoryRequest;
import com.zss.bookstore.exception.CategoryNotFoundException;
import com.zss.bookstore.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * createdBy       romeo
 * createdDate     9/8/2025
 * createdTime     09:35
 * projectName     zss-bookstore
 **/

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryObjectMapper categoryObjectMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryRequest request;
    private Category mappedCategory;

    @BeforeEach
    void setUp() {
        request = CategoryRequest.builder()
                .title("Programming")
                .build();

        mappedCategory = Category.builder()
                .title("Programming")
                .build();
    }

    @Nested
    @DisplayName("CreateCategory")
    class CreateCategoryTests {

        @Test
        @DisplayName("Should create category when request is valid")
        void shouldCreateCategory() {
            when(categoryObjectMapper.toCategory(request)).thenReturn(mappedCategory);
            when(categoryRepository.save(mappedCategory)).thenReturn(mappedCategory);

            Category result = categoryService.createCategory(request);

            assertThat(result).isSameAs(mappedCategory);
            assertThat(result.getTitle()).isEqualTo("Programming");
            verify(categoryObjectMapper).toCategory(request);
            verify(categoryRepository).save(mappedCategory);
        }

        @Test
        @DisplayName("Should throw NullPointerException when request is null")
        void shouldThrowWhenRequestNull() {
            assertThatThrownBy(() -> categoryService.createCategory(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("Category request must not be null");

            verifyNoInteractions(categoryObjectMapper, categoryRepository);
        }
    }

    @Nested
    @DisplayName("getCategoryById")
    class GetCategoryByIdTests {

        @Test
        @DisplayName("Should return category when found")
        void shouldReturnCategory() {
            Category category = Category.builder().id(1L).title("Shona").build();
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

            Category result = categoryService.getCategoryById(1L);

            assertThat(result).isSameAs(category);
            verify(categoryRepository).findById(1L);
        }

        @Test
        @DisplayName("Should throw when category not found")
        void shouldThrowWhenNotFound() {
            when(categoryRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> categoryService.getCategoryById(0L))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessageContaining(String.format("Category with id %d not found", 0L));

            verify(categoryRepository).findById(0L);
        }

        @Test
        @DisplayName("should throw NullPointerException when id is null")
        void shouldThrowWhenIdNull() {
            assertThatThrownBy(() -> categoryService.getCategoryById(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("Category id must not be null");

            verifyNoInteractions(categoryRepository);
        }
    }

    @Nested
    @DisplayName("Should get all categories")
    class GetAllCategoriesTests {

        @Test
        @DisplayName("Should return all categories")
        void shouldReturnAll() {
            List<Category> data = List.of(
                    Category.builder().id(1L).title("A").build(),
                    Category.builder().id(2L).title("B").build()
            );
            when(categoryRepository.findAll()).thenReturn(data);

            List<Category> result = categoryService.getAllCategories();

            assertThat(result).hasSize(2).extracting(Category::getTitle).containsExactly("A", "B");
            verify(categoryRepository).findAll();
        }
    }
}