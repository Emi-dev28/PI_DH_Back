package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    //@NotBlank
    @JsonIgnore
    private String name;

  //  @OneToMany(mappedBy = "category")
    //@JsonManagedReference
    //private Set<SubCategory> subCategory;
    @Column(name = "DESCRIPCION")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonIgnore
    // Le doy la posibilidad de que sea null, para aquellas categorias que se crean sin ningun producto asignado todavia.
    @Nullable
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
    public Category(String name, String description,Set<Product> products, Imagen img) {
        this.name = name;
        this.description = description;
        this.products = products;
        this.img = img;
    }
}
