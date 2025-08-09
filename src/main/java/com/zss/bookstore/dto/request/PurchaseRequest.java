package com.zss.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:10
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @NotBlank
    @NotEmpty
    private String cardId;
    @NotBlank
    @NotEmpty
    private String expiry;
}
