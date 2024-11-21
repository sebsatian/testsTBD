package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;

public interface CategoryRepository {
    CategoryEntity findCategoryById(long category_id);

    //conseguir cliente por nombre
    CategoryEntity findCategoryByName(String category_name);

    //guardado
    void saveCategory(CategoryEntity category);

    //alterar por Id
    void alterCategoryById(CategoryEntity category);

    //borrar por Id
    void deleteCategoryById(long category_id);
}
