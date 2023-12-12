package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Producto")
@Entity
@Builder
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties
    private Long id;
    @Column(name = "NOMBRE")
    @Size(min = 5 , max = 80, message = "The name mus be between 5 and 80 characters")
    @NotBlank(message = "The name cannot be blank")
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "DESCRIPCION")
    @NotBlank(message = "The description cannot be blank")
    @Size(min = 20, max = 256, message = "The description must be between 20 and 256 characters")
    @JsonProperty(value = "description")
    private String description;

    @Column(name = "PRECIO")
    @NotNull(message = "The price cannot be null ")
    @JsonProperty(value = "price")
    private Double price;
    @Column(name = "CATEGORIA")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_CATEGORIES",
            joinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id")
            }
    )
    @JsonIgnore
    @JsonProperty("categories")
    private Set<Category> categories;
    @Column(name = "RATING")
    private Double rating;
    //La opción que puede ser útil para la eliminación en cascada es cascade. Esencialmente la cascada nos permite definir qué operación (persistir, fusionar, eliminar) en la entidad padre debe ser aplicada en cascada a las entidades hijas relacionadas.
    @Column
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) //Además de utilizar
    @JoinColumn(name = "imagen_id")
    @JsonManagedReference
    @Nullable
    private Set<Imagen> imagenes;

    @Column(name = "STOCK")
    @JsonProperty(value = "stock")
    @Positive
    private Integer stock;

    @Column
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "characteristic_id")
    @JsonManagedReference
    @Nullable
    private List<Characteristic> characteristics;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "product_availability")
    @JsonProperty("product_availability")
    @JsonManagedReference
    @JoinColumn(name = "product_availability_id")
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductAvailability> availability;


    @Nullable
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "reserves")
    @JsonManagedReference
    private Set<Reserve> reserves;

    @Nullable
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "favorites")
    @JsonManagedReference
    private Set<Favorite> favorites;

    private boolean isReserved;

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", rating=" + rating +
                ", imagenes=" + imagenes +
                ", stock=" + stock +
                ", characteristics=" + characteristics +
                ", availability=" + availability +
                ", isReserved=" + isReserved +
                '}';
    }

    public Product(String name, String description, Double price, Set<Category> categories, Double rating, Set<Imagen> imagenes, Integer stock, List<Characteristic> characteristics, Set<ProductAvailability> availability, Set<Reserve> reserves, Set<Favorite> favorites, boolean isReserved) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = new HashSet<>() ;
        this.rating = rating;
        this.imagenes = new HashSet<>() ;
        this.stock = stock;
        this.characteristics = new ArrayList<>();
        this.availability = new HashSet<>();
        this.reserves = new HashSet<>();
        this.favorites = new HashSet<>();
        this.isReserved = isReserved;
    }

}
