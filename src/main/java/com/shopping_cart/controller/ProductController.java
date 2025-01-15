package com.shopping_cart.controller;

import com.shopping_cart.dto.ProductDto;
import com.shopping_cart.dto.UpdateProductDto;
import com.shopping_cart.entity.Product;
import com.shopping_cart.response.ApiResponse;
import com.shopping_cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ApiResponse registerProduct(@RequestBody ProductDto product) {
        Product registeredProduct = productService.addProduct(product);
        return new ApiResponse(true, "Product added successfully!", registeredProduct);
    }

    @GetMapping
    public ApiResponse getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(allProducts);
        return new ApiResponse(true,"products Found",convertedProducts);
    }

    @GetMapping("/product/{productId}")
    public ApiResponse getProductById(@PathVariable("productId") Long productId) {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
        return new ApiResponse(true,"Found",productDto);
    }

    @PutMapping("/update/{productId}")
    public ApiResponse updateProduct(@PathVariable Long productId, UpdateProductDto updateProductDto) {
        Product updatedProduct = productService.update(productId, updateProductDto);
        return new ApiResponse(true,"update success!", updatedProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public ApiResponse deleteProduct(@PathVariable Long productId){
            productService.delete(productId);
            return new ApiResponse(true,"deletion success!", null);
    }

    @GetMapping("/filter")
    public ApiResponse getProductsByBrandAndName(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String name) {
        List<Product> products = productService.getProductsByBrandAndName(brand, name);
        List<ProductDto> productDtoList = productService.getConvertedProducts(products);
        return new ApiResponse(true,"Success",productDtoList);
    }

    @GetMapping("/category")
    public ApiResponse getProductsByCategoryName(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductDto> productDtoList = productService.getConvertedProducts(products);
        return new ApiResponse(true,"Success",productDtoList);
    }


    @GetMapping("/brand")
    public ApiResponse getProductsByBrandName(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        List<ProductDto> productDtoList = productService.getConvertedProducts(products);
        return new ApiResponse(true,"Success",productDtoList);
    }

    @GetMapping("/category/brand")
    public ApiResponse getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if(products.isEmpty()){
                return new ApiResponse(false,"product not found!",null);
            }
            List<ProductDto> productDtoList = productService.getConvertedProducts(products);
            return new ApiResponse(true,"success!", productDtoList);
    }

    @GetMapping("/name")
    public ApiResponse getProductsByName(@RequestParam String name) {
        List<Product> products = productService.getProductsByName(name);
        if (products.isEmpty()) {
            return new ApiResponse(false, "no product found!", null);
        }
        List<ProductDto> productDtoList = productService.getConvertedProducts(products);
        return new ApiResponse(true, "success!", productDtoList);
    }
}
