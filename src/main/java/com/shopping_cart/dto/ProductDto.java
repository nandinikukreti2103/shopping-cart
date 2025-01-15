package com.shopping_cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ProductDto {

    private Long id;
    private String productName;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private CategoryDto category;
    private List<ImageDto> image;

}
