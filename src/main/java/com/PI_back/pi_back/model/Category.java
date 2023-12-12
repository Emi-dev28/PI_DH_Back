package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    //@NotBlank
    @JsonProperty(value = "name")
    private String name;

  //  @OneToMany(mappedBy = "category")
    //@JsonManagedReference
    //private Set<SubCategory> subCategory;
    @JsonProperty(value = "description")
    @Column(name = "DESCRIPCION")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL/*, mappedBy = "categories"*/)
    // Le doy la posibilidad de que sea null, para aquellas categorias que se crean sin ningun producto asignado todavia.
    @JoinColumn(name = "product_id")
    @Nullable
    @JsonBackReference
    // El set te permite asegurarte que cada item es unico en la lista
    private Set<Product> products;
//
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    @Nullable
    @JsonIgnore
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
        this.products = new HashSet<>();
        this.img = img;
    }


}
