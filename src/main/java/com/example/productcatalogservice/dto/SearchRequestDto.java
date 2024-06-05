package com.example.productcatalogservice.dto;

import com.example.productcatalogservice.models.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;

    private int pageNumber;

    private int pageSize;

    private List<SortParam> sortParamList = new ArrayList<>();
}
