package com.zss.bookstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:08
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private Long categoryId;
    private String title;
    private String description;
    private String author;
    private BigDecimal price;
}
