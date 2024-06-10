package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.UserDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StorageProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        UserDto userDto =
                restTemplate.getForEntity("http://UserAuthenticationService/users/{uid}", UserDto.class, userId).getBody();
        System.out.println("Email: " + userDto.getEmail());

        if(userDto == null)
            return null;

        return productRepository.findProductById(productId);
    }
}
