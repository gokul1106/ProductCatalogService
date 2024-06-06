package com.example.productcatalogservice.controller;

import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    private List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("{id}")
    private ResponseEntity<Product> getProduct(@PathVariable("id") Long productId){
        if(productId < 1){
            throw new IllegalArgumentException("id is invalid");
        }
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @PostMapping
    private Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(getProduct(productDto));
    }

    @PutMapping("{id}")
    private Product replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        return productService.replaceProduct(id, getProduct(productDto));
    }

    @GetMapping("{pid}/{uid}")
    private ProductDto getProductDetails(@PathVariable Long pid, @PathVariable Long uid){
        Product product = productService.getProductDetails(pid,uid);
        return getProductDto(product);
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImage());
        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null){
            Category category = new Category();
            category.setName(product.getCategory().getName());
            category.setDescription(product.getCategory().getDescription());
            product.setCategory(category);
        }
        return productDto;
    }
}
