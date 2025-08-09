package com.zss.bookstore.integration;

import lombok.*;
import java.math.BigDecimal;
import java.util.Map;


/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     18:55
 * projectName     zss-bookstore
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String type;
    private String extendedType;
    private BigDecimal amount;
    private String created;
    private Card card;
    private String reference;
    private String narration;
    private Map<String, Object> additionalData;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Card {
        private String id;
        private String expiry;
    }
}
