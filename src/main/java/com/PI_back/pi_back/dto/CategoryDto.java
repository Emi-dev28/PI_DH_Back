package com.PI_back.pi_back.dto;

import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor

public class CategoryDto {

    private Long id;

    private String name;
    private String description;
    private Set<Product> products;
    private String urlImg;


    public CategoryDto(Long id, String name, String description, Set<Product> products, String urlImg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.urlImg = urlImg;
    }

    public CategoryDto(Long id,String description, Set<Product> products, String urlImg) {
        this.id = id;
        this.description = description;
        this.products = products;
        this.urlImg =urlImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(products, that.products) && Objects.equals(urlImg, that.urlImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, products, urlImg);
    }
}
