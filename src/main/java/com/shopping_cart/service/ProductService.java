package com.shopping_cart.service;

import com.shopping_cart.dto.ProductDto;
import com.shopping_cart.dto.UpdateProductDto;
import com.shopping_cart.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductDto product);
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product update(Long productId, UpdateProductDto updateProductDto);
    void delete(Long productId);

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
