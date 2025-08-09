package com.zss.bookstore.service.impl;

import org.junit.jupiter.api.Test;
import com.zss.bookstore.convertor.BookObjectMapper;
import com.zss.bookstore.domain.Book;
import com.zss.bookstore.domain.Category;
import com.zss.bookstore.dto.request.BookRequest;
import com.zss.bookstore.exception.BookNotFoundException;
import com.zss.bookstore.exception.CategoryNotFoundException;
import com.zss.bookstore.persistence.BookRepository;
import com.zss.bookstore.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * createdBy       romeo
 * createdDate     9/8/2025
 * createdTime     09:31
 * projectName     zss-bookstore
 **/

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookObjectMapper bookObjectMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequest bookRequest;
    private Category category;
    private Book mappedBook;

    @BeforeEach
    void init() {
        bookRequest = BookRequest.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .description("A Handbook of Agile Software Craftsmanship")
                .price(new BigDecimal("45.00"))
                .categoryId(1L)
                .build();

        category = Category.builder()
                .id(1L)
                .title("Programming")
                .build();

        mappedBook = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .description(bookRequest.getDescription())
                .price(bookRequest.getPrice())
                .build();
    }

    @Nested
    @DisplayName("createBook")
    class CreateBookTests {

        @Test
        @DisplayName("should create book when category exists")
        void shouldCreateBook() {
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(bookObjectMapper.toBook(bookRequest)).thenReturn(mappedBook);

            Book saved = Book.builder()
                    .id(100L)
                    .title(mappedBook.getTitle())
                    .author(mappedBook.getAuthor())
                    .description(mappedBook.getDescription())
                    .price(mappedBook.getPrice())
                    .category(category)
                    .build();

            when(bookRepository.save(any(Book.class))).thenReturn(saved);

            Book result = bookService.createBook(bookRequest);

            assertThat(result.getId()).isEqualTo(100L);
            assertThat(result.getCategory()).isNotNull();
            assertThat(result.getCategory().getId()).isEqualTo(1L);
            assertThat(result.getTitle()).isEqualTo("Clean Code");

            verify(categoryRepository).findById(1L);
            verify(bookObjectMapper).toBook(bookRequest);
            ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
            verify(bookRepository).save(bookCaptor.capture());
            assertThat(bookCaptor.getValue().getCategory()).isEqualTo(category);
        }

        @Test
        @DisplayName("should throw when category not found")
        void shouldThrowWhenCategoryNotFound() {
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookService.createBook(bookRequest))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessageContaining("Category with id 1 not found");

            verify(bookRepository, never()).save(any());
            verify(bookObjectMapper, never()).toBook(any());
        }

        @Test
        @DisplayName("should throw NPE when request is null")
        void shouldThrowWhenRequestNull() {
            assertThatThrownBy(() -> bookService.createBook(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("bookRequest must not be null");
        }
    }

    @Nested
    @DisplayName("getBookById")
    class GetBookByIdTests {

        @Test
        @DisplayName("should return book when found")
        void shouldReturnBook() {
            Book book = Book.builder().id(5L).title("DDD").build();
            when(bookRepository.findById(5L)).thenReturn(Optional.of(book));

            Book result = bookService.getBookById(5L);

            assertThat(result).isSameAs(book);
            verify(bookRepository).findById(5L);
        }

        @Test
        @DisplayName("Should throw when book not found")
        void shouldThrowWhenNotFound() {
            when(bookRepository.findById(9L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookService.getBookById(9L))
                    .isInstanceOf(BookNotFoundException.class)
                    .hasMessageContaining("Book with id 9 not found");
        }

        @Test
        @DisplayName("Should throw NullPointerException when id is null")
        void shouldThrowWhenIdNull() {
            assertThatThrownBy(() -> bookService.getBookById(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("bookId must not be null");
        }
    }

    @Nested
    @DisplayName("GetBooksByCategory")
    class GetBooksByCategoryTests {

        @Test
        @DisplayName("Should return paged books for category id")
        void shouldReturnPagedBooks() {
            Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));
            List<Book> data = List.of(
                    Book.builder().id(1L).title("A").build(),
                    Book.builder().id(2L).title("B").build()
            );
            Page<Book> page = new PageImpl<>(data, pageable, 5);
            when(bookRepository.findByCategoryId(1L, pageable)).thenReturn(page);

            Page<Book> result = bookService.getBooksByCategory(1L, pageable);

            assertThat(result.getContent()).hasSize(2);
            assertThat(result.getTotalElements()).isEqualTo(5);
            verify(bookRepository).findByCategoryId(1L, pageable);
        }

        @Test
        @DisplayName("Should throw NPE when categoryId is null")
        void shouldThrowWhenCategoryIdNull() {
            Pageable pageable = PageRequest.of(0, 10);
            assertThatThrownBy(() -> bookService.getBooksByCategory(null, pageable))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("categoryId must not be null");
        }
    }

    @Nested
    @DisplayName("GetAllBooks")
    class GetAllBooksTests {

        @Test
        @DisplayName("Should return paged books")
        void shouldReturnPagedBooks() {
            Pageable pageable = PageRequest.of(0, 3);
            List<Book> data = List.of(
                    Book.builder().id(10L).title("X").build(),
                    Book.builder().id(11L).title("Y").build()
            );
            Page<Book> page = new PageImpl<>(data, pageable, 2);
            when(bookRepository.findAll(pageable)).thenReturn(page);

            Page<Book> result = bookService.getAllBooks(pageable);

            assertThat(result.getContent()).extracting(Book::getId).containsExactly(10L, 11L);
            assertThat(result.getTotalElements()).isEqualTo(2);
            verify(bookRepository).findAll(pageable);
        }
    }
}