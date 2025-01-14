package com.shopping_cart.controller;

import com.shopping_cart.entity.Category;
import com.shopping_cart.response.ApiResponse;
import com.shopping_cart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse addCategory(@RequestBody Category category) {
            Category categoryCreated = categoryService.addCategory(category);
            return new ApiResponse(true,"Category successfully added!", categoryCreated);
    }


    @GetMapping
    public ApiResponse getAllCategory(){
            List<Category> allCategory = categoryService.getAllCategories();
            return new ApiResponse(true,"Found!", allCategory);
    }

    @GetMapping("/{categoryId}")
    public ApiResponse getCategoryById(@PathVariable Long categoryId){
            Category category = categoryService.getCategoryById(categoryId);
            return new ApiResponse(true,"Found!", category);
    }

    @PutMapping("/update/{categoryId}")
    public ApiResponse updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
            Category updatedCategory = categoryService.update(categoryId,category);
            return new ApiResponse(true,"category updated!", updatedCategory);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ApiResponse deleteCategory(@PathVariable Long categoryId) {
            categoryService.delete(categoryId);
            return new ApiResponse(true,"category deleted!", null);

    }

    @GetMapping("/name")
    public ApiResponse getCategoryByName(@RequestParam String name) {
            Category category = categoryService.getCategoryByName(name);
            return new ApiResponse(true,"Found by name!", category);
    }

}
