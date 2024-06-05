package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();

    Product getProduct(Long id);

    Product createProduct(Product product);

    Product replaceProduct(Long id, Product product);
}
