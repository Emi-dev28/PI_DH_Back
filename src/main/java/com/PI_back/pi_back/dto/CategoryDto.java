package com.PI_back.pi_back.dto;

import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class CategoryDto {

    private String name;
    private String description;
    private Set<Product> products;
    private Imagen img;

}
