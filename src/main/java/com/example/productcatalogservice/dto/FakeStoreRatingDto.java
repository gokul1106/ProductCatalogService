package com.example.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FakeStoreRatingDto
{
    private Double rate;
    private Long count;
}
