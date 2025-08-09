package com.zss.bookstore.service;

import com.zss.bookstore.dto.request.PurchaseRequest;
import org.springframework.http.ResponseEntity;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     12:14
 * projectName     zss-bookstore
 **/

public interface PurchaseService {
    ResponseEntity<?> purchaseBook(Long bookId, PurchaseRequest purchaseRequest);
}
