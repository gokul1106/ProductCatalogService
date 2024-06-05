package com.example.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
