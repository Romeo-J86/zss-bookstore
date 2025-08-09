package com.zss.bookstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:09
 * projectName     zss-bookstore
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String title;
}
