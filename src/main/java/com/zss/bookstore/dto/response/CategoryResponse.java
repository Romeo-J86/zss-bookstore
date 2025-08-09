package com.zss.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:55
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String title;
}
