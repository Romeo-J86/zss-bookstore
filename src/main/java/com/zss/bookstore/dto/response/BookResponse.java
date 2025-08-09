package com.zss.bookstore.dto.response;

import com.zss.bookstore.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:44
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Category category;
}
