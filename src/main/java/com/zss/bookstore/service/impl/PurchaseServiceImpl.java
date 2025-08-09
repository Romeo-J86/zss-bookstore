package com.zss.bookstore.service.impl;

import com.zss.bookstore.dto.request.PurchaseRequest;
import com.zss.bookstore.integration.TransactionResponse;
import com.zss.bookstore.integration.TransactionClient;
import com.zss.bookstore.integration.TransactionRequest;
import com.zss.bookstore.service.BookService;
import com.zss.bookstore.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     12:15
 * projectName     zss-bookstore
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final TransactionClient transactionClient;
    private final BookService bookService;

    @Override
    public ResponseEntity<?> purchaseBook(Long bookId, PurchaseRequest purchaseRequest) {
        requireNonNull(bookId, "bookId must not be null");
        requireNonNull(purchaseRequest, "purchaseRequest must not be null");

        log.info("Purchase book with id: {}", bookId);
        var book = bookService.getBookById(bookId);
        if (book == null) return ResponseEntity.badRequest()
                .body(singletonMap("error", "book not found"));

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .type("PURCHASE")
                .extendedType("NONE")
                .amount(book.getPrice())
                .created(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .card(TransactionRequest.Card.builder()
                        .id(purchaseRequest.getCardId())
                        .expiry(purchaseRequest.getExpiry())
                        .build())
                .reference(UUID.randomUUID().toString())
                .narration("Purchase book: " + book.getTitle())
                .additionalData(singletonMap("bookId", book.getId()))
                .build();

        try {
            TransactionResponse resp = transactionClient.processTransaction(transactionRequest)
                    .block();
            log.info("Transaction response: {}", resp);
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            return ResponseEntity.status(BAD_GATEWAY)
                    .body(singletonMap("error",
                            format("Payment Gateway Error: %s",
                                    ex.getMessage())));
        }
    }
}
