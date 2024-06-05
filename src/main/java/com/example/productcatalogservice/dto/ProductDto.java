package com.example.productcatalogservice.dto;

import com.example.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link com.example.productcatalogservice.models.Product}
 */
@Getter
@Setter
public class ProductDto {
    Long id;
    String name;
    String title;
    String description;
    Double price;
    private String imageUrl;
    private String image;
    private CategoryDto category;
    private RatingDto rating;
}