package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Primary
@Service
public class FakeStoreProductService implements IProductService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<Product> getAllProduct(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        List<Product> productList = new ArrayList<Product>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtos){
            productList.add(getProduct(fakeStoreProductDto));
        }
        return productList;
    }

    @Override
    public Product getProduct(Long productId){
//        Check if product is in cache
//                return product;
//        else
//            get from API
        FakeStoreProductDto fakeStoreProductDto = null;
        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get("PRODUCTS",productId);
        if(fakeStoreProductDto == null){
            System.out.println("Not found in cache");
            RestTemplate restTemplate = restTemplateBuilder.build();
            fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId).getBody();
            redisTemplate.opsForHash().put("PRODUCTS",productId,fakeStoreProductDto);
            System.out.println("Added to cache");
        }
        else
            System.out.println("Found in cache");
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoReq = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products",fakeStoreProductDtoReq,FakeStoreProductDto.class).getBody();
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoReq = getFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> responseEntity =
                putForEntity("https://fakestoreapi.com/products/{id}",fakeStoreProductDtoReq,FakeStoreProductDto.class,id);
        return getProduct(responseEntity.getBody());
    }

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        return null;
    }

    public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    private Product getProduct(FakeStoreProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImage());
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setName(product.getName());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        return fakeStoreProductDto;
    }
}
