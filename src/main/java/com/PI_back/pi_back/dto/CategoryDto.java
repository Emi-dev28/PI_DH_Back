package com.PI_back.pi_back.dto;

import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto {

    private String name;
    private String description;
    private Set<Product> products;
    private Imagen img;


    public CategoryDto(String name, String description, Set<Product> products, Imagen img) {
        this.name = name;
        this.description = description;
        this.img = img;
    }

    public CategoryDto(String description, Set<Product> products, Imagen img) {
        this.description = description;
        this.products = products;
        this.img = img;
    }
}
