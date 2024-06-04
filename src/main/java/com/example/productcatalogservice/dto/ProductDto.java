package com.example.productcatalogservice.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.productcatalogservice.models.Product}
 */
@Value
public class ProductDto implements Serializable {
    Long id;
    String title;
    String description;
    Double price;
}