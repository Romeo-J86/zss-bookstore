package com.zss.bookstore.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:12
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String updated;
    private String responseCode;
    private String responseDescription;
    private String reference;
    private String debitReference;
}
