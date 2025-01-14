package com.shopping_cart.dto;

import com.shopping_cart.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
