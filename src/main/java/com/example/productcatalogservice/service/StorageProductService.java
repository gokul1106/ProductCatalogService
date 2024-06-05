package com.example.productcatalogservice.service;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class StorageProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAllProducts();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Product createProduct(Product product) {
        Product resultProduct = productRepository.save(product);
        return resultProduct;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }
}
