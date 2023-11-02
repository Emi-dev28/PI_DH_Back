package com.PI_back.pi_back.dto;


import com.PI_back.pi_back.model.Category;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDto {

    // todo: chequear notaciones en entidades, services y repository.
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Set<Category> category;
    private String stock;
    private List<String> characteristics;

}
