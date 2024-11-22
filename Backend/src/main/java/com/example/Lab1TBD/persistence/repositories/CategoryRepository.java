package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;

public interface CategoryRepository {
    CategoryEntity findCategoryById(long category_id);
    CategoryEntity findCategoryByName(String category_name);

    void saveCategory(CategoryEntity category);
    void updateCategory(CategoryEntity category);
    void deleteCategoryById(long category_id);
}
