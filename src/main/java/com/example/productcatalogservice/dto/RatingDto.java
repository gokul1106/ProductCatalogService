package com.example.productcatalogservice.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RatingDto {
    private Double rate;
    private Long count;
}
