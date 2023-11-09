package com.PI_back.pi_back.dto;


import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Imagen;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    // todo: chequear notaciones en entidades, services y repository.
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private double price;

    @JsonProperty("category")
    private Set<Category> categories = new HashSet<>();

    private Set<Imagen> images = new HashSet<>();
    @JsonProperty("stock")
    private int stock;
    @JsonProperty("characteristics")
    private List<String> characteristics;

}
