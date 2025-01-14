package com.shopping_cart.repository;

import com.shopping_cart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findByName(String name);

    boolean existsByName(String name);

}