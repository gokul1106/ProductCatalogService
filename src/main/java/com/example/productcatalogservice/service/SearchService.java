package com.example.productcatalogservice.service;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.SortParam;
import com.example.productcatalogservice.models.SortType;
import com.example.productcatalogservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> searchProducts(String query, int pageSize, int pageNumber, List<SortParam> sortParams) {
        //Descending order of price and if price is same, then ascending order of id
        //Sort sort = Sort.by("price").descending().and(Sort.by("id"));
        Sort sort = null;

        if(!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASC))
                sort = Sort.by(sortParams.get(0).getParamName());
            else
                sort = Sort.by(sortParams.get(0).getParamName()).descending();
        }

        for(int i=1; i<sortParams.size();i++) {
            if(sortParams.get(i).getSortType().equals(SortType.ASC))
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()));
            else
                sort = sort.and(sort.by(sortParams.get(i).getParamName()).descending());
        }
        System.out.println(query);
        Page<Product> result = productRepository.findByNameContainingIgnoreCaseOrTitleContainingIgnoreCase(query, query, PageRequest.of(pageNumber,pageSize,sort));
        return result;
    }
}
