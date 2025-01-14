package com.shopping_cart.service.impl;

import com.shopping_cart.dto.ProductDto;
import com.shopping_cart.dto.UpdateProductDto;
import com.shopping_cart.entity.Category;
import com.shopping_cart.entity.Product;
import com.shopping_cart.exception.CategoryNotFoundException;
import com.shopping_cart.exception.ProductNotFoundException;
import com.shopping_cart.repository.CategoryRepository;
import com.shopping_cart.repository.ProductRepository;
import com.shopping_cart.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

//    @Override
//    public Product addProduct(ProductDto product) {
//        //check : category exist in db
//        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
//                .orElseGet(() -> {
//                    //if no: then create it as a new category and save in the db
//                    Category newCategory = new Category(product.getCategory().getName());
//                    return categoryRepository.save(newCategory);
//                });
//        //set the category for product and save it using createProduct method
//        product.setCategory(category);
//        return productRepository.save(createProduct(product, category));
//    }

    @Override
    @Transactional
    public Product addProduct(ProductDto productDto) {
        try {
            if (productDto.getCategory() == null || productDto.getCategory().getName() == null) {
                throw new IllegalArgumentException("Category name cannot be null");
            }

            // Check if category exists, otherwise create it
            Category category = Optional.ofNullable(categoryRepository.findByName(productDto.getCategory().getName()))
                    .orElseGet(() -> {
                        Category newCategory = new Category(productDto.getCategory().getName());
                        Category savedCategory = categoryRepository.save(newCategory);
                        System.out.println("New category saved: " + savedCategory.getName());  // Add this log to track the category saving process
                        return savedCategory;
                    });

            // Create a new Product entity and set its fields
            Product product = new Product();
            product.setProductName(productDto.getProductName());
            product.setBrand(productDto.getBrand());
            product.setPrice(productDto.getPrice());
            product.setInventory(productDto.getInventory());
            product.setDescription(productDto.getDescription());
            product.setCategory(category); // Set the category for the product

            // Save the product
            Product savedProduct = productRepository.save(product);
            System.out.println("Product saved: " + savedProduct.getProductName());

            return savedProduct;
        } catch (Exception e) {
            e.printStackTrace(); // Log the error stack trace
            throw new RuntimeException("Error while adding product: " + e.getMessage());
        }
    }


//    private Product createProduct(ProductDto productDto, Category category) {
//        return new Product(
//                productDto.getProductName(),
//                productDto.getBrand(),
//                productDto.getPrice(),
//                productDto.getInventory(),
//                productDto.getDescription(),
//                category
//        );
//    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product not found with this id: " + productId));

    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Long productId, UpdateProductDto updateProductDto) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, updateProductDto))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductDto updateProductDto) {
        existingProduct.setProductName(updateProductDto.getName());
        existingProduct.setBrand(updateProductDto.getBrand());
        existingProduct.setPrice(updateProductDto.getPrice());
        existingProduct.setInventory(updateProductDto.getInventory());
        existingProduct.setDescription(updateProductDto.getDescription());

        Category category = categoryRepository.findByName(updateProductDto.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product does not exist in the db."));

        productRepository.delete(product);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);

        if (products.isEmpty()) {
            throw new CategoryNotFoundException("No products found for category: " + category);
        }

        return products;
    }


    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found for brand: "  + brand);
        }
        return  products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
         List<Product> products= productRepository.findByCategoryNameAndBrand(category, brand);
         if(products.isEmpty()){
             throw new ProductNotFoundException("No product found for this category and brand: " + category + "and brand" +brand);
         }
         return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products = productRepository.findByProductName(name);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No Product found for this name: " + name);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndProductName(brand, name);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No product found for this brand and name: " + brand + "and" + name);
        }
        return products;
    }

//    @Override
//    public Long countProductsByBrandAndName(String brand, String name) {
//        return productRepository.countProductsByBrandAndName(brand,name);
//    }
}
