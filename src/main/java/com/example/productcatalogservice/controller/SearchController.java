package com.example.productcatalogservice.controller;

import com.example.productcatalogservice.dto.CategoryDto;
import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.dto.SearchRequestDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public Page<Product> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        /*List<ProductDto> searchResults = new ArrayList<>();
        List<Product> products = searchService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageSize(), searchRequestDto.getPageNumber());
        for(Product product : products) {
            searchResults.add(getProductDto(product));
        }
        return searchResults;*/

        return searchService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageSize(), searchRequestDto.getPageNumber(), searchRequestDto.getSortParamList());
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return  productDto;
    }
}
