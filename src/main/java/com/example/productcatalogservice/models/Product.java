package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

@Getter
@Setter
@Entity
//Adding this to remove all null entries from postman json result
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel{
    private String name;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Category category;

    private Boolean isSpecial;
}
