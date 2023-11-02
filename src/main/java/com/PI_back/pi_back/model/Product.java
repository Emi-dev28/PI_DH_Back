package com.PI_back.pi_back.model;

import com.PI_back.pi_back.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "Producto")
@Entity
@Builder
public class Product {
   // todo: mappear las relaciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOMBRE")
    @Size(max = 80)
    @NotBlank
    private String name;

    @Column(name = "DESCRIPCION")
    @Size(max = 256)
    private String description;

    @Column(name = "PRECIO")
    @NotBlank
    private Double price;
    @Column(name = "CANTIDAD")
    @NotBlank
    private Integer quantity;
    @Column(name = "CATEGORIA")
    @NotBlank
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Category> categories;
    @Column(name = "RATING")
    private Double rating;


    //La opción que puede ser útil para la eliminación en cascada es cascade. Esencialmente la cascada nos permite definir qué operación (persistir, fusionar, eliminar) en la entidad padre debe ser aplicada en cascada a las entidades hijas relacionadas.
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) //Además de utilizar CascadeType.All o CascadeType.remove, es esencial establecer la propiedad orphanRemoval a true para asegurar la correcta eliminación de las entidades huérfanas. Con esta propiedad establecida, JPA elimina automáticamente cualquier entidad huérfana de la base de datos
    @JoinColumn(name = "imagen_id")
    @JsonManagedReference
    private Set<Imagen> imagenes = new HashSet<>();
    @Column(name = "stock")
    private String stock;

    @Column
    private List<String> characteristics;


 public Product(Long id, String name, String description, Double price, Integer quantity, Set<Category> categories, Double rating, Set<Imagen> imagenes, String stock, List<String> characteristics) {
  this.id = id;
  this.name = name;
  this.description = description;
  this.price = price;
  this.quantity = quantity;
  this.categories = new HashSet<>();
  this.rating = rating;
  this.imagenes = new HashSet<>();
  this.stock = stock;
  this.characteristics = new ArrayList<>();
 }
}
