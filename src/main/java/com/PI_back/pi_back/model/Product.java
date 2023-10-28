package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "Producto")
@Entity
public class Product {
   // todo: mappear las relaciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOMBRE")
    @NotNull
    private String name;

    @Column(name = "DESCRIPCION")
    private String description;

    @Column(name = "precio")
    @NotNull
    private double price;
    @Column(name = "CANTIDAD")
    @NotNull
    private int quantity;
    @Column(name = "CATEGORIA")
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category category;
    @Column(name = "RATING")
    private double rating;


    //La opción que puede ser útil para la eliminación en cascada es cascade. Esencialmente la cascada nos permite definir qué operación (persistir, fusionar, eliminar) en la entidad padre debe ser aplicada en cascada a las entidades hijas relacionadas.
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) //Además de utilizar CascadeType.All o CascadeType.remove, es esencial establecer la propiedad orphanRemoval a true para asegurar la correcta eliminación de las entidades huérfanas. Con esta propiedad establecida, JPA elimina automáticamente cualquier entidad huérfana de la base de datos
    @JoinColumn(name = "imagen_id")
    @JsonManagedReference
    private Set<Imagen> imagenes = new HashSet<>();
    @Column(name = "stock")
    private String stock;


 public Product(String name, String description, double price, int quantity, Category category, double rating, Set<Imagen> imagenes, String stock) {
  this.name = name;
  this.description = description;
  this.price = price;
  this.quantity = quantity;
  this.category = category;
  this.rating = rating;
  this.imagenes = imagenes;
  this.stock = stock;
 }
}
