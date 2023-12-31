package com.PI_back.pi_back.dto;


import com.PI_back.pi_back.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    // todo: chequear notaciones en entidades, services y repository.
    @JsonIgnoreProperties
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private double price;
    @JsonProperty("categories")
    private Set<Category> categories;

    @JsonProperty("imagenes")
    private Set<Imagen> images;
    @JsonProperty("stock")
    private int stock;
    @JsonProperty("characteristics")
    private List<Characteristic> characteristics;
    @JsonProperty("product_availability")
    private Set<ProductAvailability> availability;
    @JsonProperty("reserves")
    private Set<Reserve> reserves;
    @JsonProperty("favorites")
    private Set<Favorite> favorites;
    @JsonProperty("Reservado")
    private boolean isReserved;

    public ProductDto(String name, String description, double price, Set<Category> categories, Set<Imagen> images, int stock, List<Characteristic> characteristics, Set<ProductAvailability> availability, boolean isReserved) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = new HashSet<>();
        this.images = new HashSet<>();
        this.stock = stock;
        this.characteristics = new ArrayList<>();
        this.availability = availability;
        this.isReserved = isReserved;
    }

    public ProductDto(String name, String description, double price, Set<Category> categories, Set<Imagen> images, int stock, List<Characteristic> characteristics, Set<ProductAvailability> availability, Set<Reserve> reserves, Set<Favorite> favorites, boolean isReserved) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = new HashSet<>();
        this.images = new HashSet<>();
        this.stock = stock;
        this.characteristics = new ArrayList<>();
        this.availability = availability;
        this.reserves = new HashSet<>();
        this.favorites = new HashSet<>();
        this.isReserved = isReserved;
    }
}
