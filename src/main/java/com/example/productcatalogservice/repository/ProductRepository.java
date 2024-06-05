package com.example.productcatalogservice.repository;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product save(Product product);

    @Query("select p,c from Product p join Category c on p.category.id=c.id")
    List<Product> findAllProducts();

    Product findProductById(Long id);

    List<Product> findProductByPriceBetween(Double low, Double high);

    List<Product> findAllByOrderByIdDesc();
}
