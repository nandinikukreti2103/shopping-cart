package com.shopping_cart.service;

import com.shopping_cart.entity.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category update(Long categoryId, Category categoryDetails);

    void delete(Long categoryId);

    Category getCategoryByName(String name);
}
