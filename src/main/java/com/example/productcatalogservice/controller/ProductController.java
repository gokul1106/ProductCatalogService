package com.example.productcatalogservice.controller;

import com.example.productcatalogservice.dto.ProductDto;
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
        try{
            if(productId < 1){
                throw new IllegalArgumentException("id is invalid");
            }
            Product product = productService.getProduct(productId);
            return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
        }
        catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    private Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }
}
