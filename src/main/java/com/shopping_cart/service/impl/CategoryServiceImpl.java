package com.shopping_cart.service.impl;

import com.shopping_cart.entity.Category;
import com.shopping_cart.exception.AlreadyExistException;
import com.shopping_cart.exception.CategoryNotFoundException;
import com.shopping_cart.repository.CategoryRepository;
import com.shopping_cart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()-> new AlreadyExistException(category.getName()+ "already exist"));
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("category not found with this id: " + categoryId));
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category update(Long categoryId, Category categoryDetails) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(existingCategory-> {
                    existingCategory.setName(categoryDetails.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(()-> new CategoryNotFoundException("category not found!"));
    }

    @Override
    public void delete(Long categoryId) {
       categoryRepository.findById(categoryId)
               .ifPresentOrElse(categoryRepository :: delete, ()->{
                   throw new CategoryNotFoundException("category does not exist");
               });

    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
