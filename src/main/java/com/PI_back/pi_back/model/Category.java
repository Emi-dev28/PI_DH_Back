package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<SubCategory> subCategory;


    @ManyToMany
    // El set te permite asegurarte que cada item es unico en la lista
    private Set<Product> productList = new HashSet<>();

}
