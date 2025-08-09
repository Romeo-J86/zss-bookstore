package com.zss.bookstore.api;

import com.zss.bookstore.convertor.BookObjectMapper;
import com.zss.bookstore.dto.request.BookRequest;
import com.zss.bookstore.dto.request.PurchaseRequest;
import com.zss.bookstore.dto.response.BookResponse;
import com.zss.bookstore.service.BookService;
import com.zss.bookstore.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:43
 * projectName     zss-bookstore
 **/

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Store", description = "EST APIs for managing a Book Store")
public class BookRestController {

    private final BookService bookService;
    private final BookObjectMapper bookObjectMapper;
    private final PurchaseService purchaseService;

    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest
                                                               bookRequest){
        return ResponseEntity.status(CREATED)
                .body(bookObjectMapper
                .toBookResponse(bookService
                        .createBook(bookRequest)));
    }

    @Operation(summary = "Get a book by its ID")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long bookId){
        return ResponseEntity.ok(bookObjectMapper
                .toBookResponse(bookService.getBookById(bookId)));
    }

    @Operation(summary = "Get all books with pagination")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(@PageableDefault(size = 8)
                                                              Pageable pageable  ){
        return ResponseEntity.ok(bookService.getAllBooks(pageable)
                .map(bookObjectMapper::toBookResponse)
                );
    }

    @Operation(summary = "Get all books by category with pagination")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<BookResponse>> getAllBooksByCategoryId(@PathVariable Long categoryId,
                                                                      @PageableDefault(size = 8)
                                                                      Pageable pageable){
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryId, pageable)
                .map(bookObjectMapper::toBookResponse)
        );
    }

    @Operation(
            summary = "Purchase a book",
            description = "Initiates a purchase for a specific book by its ID."
    )
    @PostMapping("/{bookId}/purchase")
    public ResponseEntity<?> purchaseBook(
            @Parameter(description = "ID of the book to purchase", example = "1")
            @PathVariable Long bookId,
            @Parameter(description = "Payment and card details")
            @Valid @RequestBody PurchaseRequest request) {

        return purchaseService.purchaseBook(bookId, request);
    }

}
