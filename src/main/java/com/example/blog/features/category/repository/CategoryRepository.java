package com.example.blog.features.category.repository;

import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.features.category.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends AbstractRepository<Category> {
    Boolean existsByName(String name);
}
