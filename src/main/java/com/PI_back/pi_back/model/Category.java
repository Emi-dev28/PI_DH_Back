package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "CATEGORIES")
@Data
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    //@NotBlank
    @JsonProperty(value = "name")
    @Size(min = 3, max = 50, message = "The name of the category must be between 3 and 50 characters")

    private String name;

  //  @OneToMany(mappedBy = "category")
    //@JsonManagedReference
    //private Set<SubCategory> subCategory;
    @Column(name = "DESCRIPCION")
    @Size(min = 20, max = 256, message = "The description of the category must be between 20 and 256 characters")
    @JsonProperty(value = "description")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonIgnore
    // Le doy la posibilidad de que sea null, para aquellas categorias que se crean sin ningun producto asignado todavia.
    @Nullable
    @JsonProperty("products")
    // El set te permite asegurarte que cada item es unico en la lista
    private Set<Product> products;
//
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    @Nullable
    private Imagen img;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public Category(String name, String description, Imagen img) {
        this.name = name;
        this.description = description;
        this.img = img;
    }
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Category(String name, String description,Set<Product> products, Imagen img) {
        this.name = name;
        this.description = description;
        this.products = products;
        this.img = img;
    }
}
