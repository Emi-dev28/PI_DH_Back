package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class Product {
   // todo: mappear las relaciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_CATEGORIES",
            joinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id")
            }

    )
    @JsonProperty(value = "categories")
    private Set<Category> categories;
    @Column(name = "RATING")
    private Double rating;
    //La opción que puede ser útil para la eliminación en cascada es cascade. Esencialmente la cascada nos permite definir qué operación (persistir, fusionar, eliminar) en la entidad padre debe ser aplicada en cascada a las entidades hijas relacionadas.
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) //Además de utilizar CascadeType.All o CascadeType.remove, es esencial establecer la propiedad orphanRemoval a true para asegurar la correcta eliminación de las entidades huérfanas. Con esta propiedad establecida, JPA elimina automáticamente cualquier entidad huérfana de la base de datos
    @JoinColumn(name = "imagen_id")
    @JsonManagedReference
    private Set<Imagen> imagenes = new HashSet<>();

    @Column(name = "STOCK")
    @JsonProperty(value = "stock")

    @Positive
    private Integer stock;

    @Column

    @JsonProperty(value = "characteristics")

    private List<String> characteristics;


 public Product(String name, String description, Double price, Set<Category> categories, Double rating, Set<Imagen> imagenes, Integer stock, List<String> characteristics) {
  this.name = name;
  this.description = description;
  this.price = price;
  this.categories = categories;
  this.rating = rating;
  this.imagenes = imagenes;
  this.stock = stock;
  this.characteristics = characteristics;
 }
}
