package com.shopping_cart.repository;

import com.shopping_cart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByProductName(String productName);

    List<Product> findByBrandAndProductName(String brand, String name);

    // Long countProductsByBrandAndName(String brand, String name);

}
