package com.PI_back.pi_back.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    @NotNull
    private String name;


    private SubCategory subCategory;


    @ManyToMany
    @JoinColumn(name = "producto_id")
    // El set te permite asegurarte que cada item es unico en la lista
    private Set<Product> productList = new HashSet<>();

}
