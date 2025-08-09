package com.zss.bookstore.persistence;

import com.zss.bookstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     11:05
 * projectName     zss-bookstore
 **/

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
