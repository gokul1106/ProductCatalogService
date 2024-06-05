package com.example.productcatalogservice.repository;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select p.name from Product p where p.id=:id1")
    String getProductNameFromId(@Param("id1") Long id);

    @Query("select c.name from Product p join Category c on p.category.id=c.id and p.id=?1")
    String getCategoryNameFromProductId(Long id);

    Page<Product> findByNameEqualsIgnoreCase(String query, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseOrTitleContainingIgnoreCase(String query,String query2, Pageable pageable);
}
