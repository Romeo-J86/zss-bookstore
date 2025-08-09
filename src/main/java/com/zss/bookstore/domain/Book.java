package com.zss.bookstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:01
 * projectName     zss-bookstore
 **/

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;

    @ManyToOne
    private Category category;
}
