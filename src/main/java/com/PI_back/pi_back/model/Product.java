package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIdentityReference(alwaysAsId = true)
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_CATEGORIES",
            joinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id")
            }

    )
    @JsonIgnore
    //@JsonProperty("set_of_categories")
    private Set<Category> categories;
    @Column(name = "RATING")
    private Double rating;
    //La opción que puede ser útil para la eliminación en cascada es cascade. Esencialmente la cascada nos permite definir qué operación (persistir, fusionar, eliminar) en la entidad padre debe ser aplicada en cascada a las entidades hijas relacionadas.
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) //Además de utilizar CascadeType.All o CascadeType.remove, es esencial establecer la propiedad orphanRemoval a true para asegurar la correcta eliminación de las entidades huérfanas. Con esta propiedad establecida, JPA elimina automáticamente cualquier entidad huérfana de la base de datos
    @JoinColumn(name = "imagen_id")
    //@JsonProperty("set_of_images")
    @JsonIgnore
    private Set<Imagen> imagenes = new HashSet<>();

    @Column(name = "STOCK")
    @JsonProperty(value = "stock")
    @Positive
    private Integer stock;

    @Column
    //@JsonProperty(value = "characteristics")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "characteristic_id")
    @JsonManagedReference
    // @JsonProperty("set_of_characteristics")
    private List<Characteristic> characteristics;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    // @NotBlank(...)
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "availability_id")
//    @JsonProperty("product_availability")
//    private ProductAvailability availability;

    private boolean isReserved;

// @Override
// public boolean equals(Object o) {
//  if (this == o) return true;
//  if (o == null || getClass() != o.getClass()) return false;
//  Product product = (Product) o;
//  return isReserved == product.isReserved && id.equals(product.id) && name.equals(product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(categories, product.categories) && Objects.equals(rating, product.rating) && Objects.equals(imagenes, product.imagenes) && Objects.equals(stock, product.stock) && characteristics.equals(product.characteristics) && availability.equals(product.availability);
// }

// @Override
// public int hashCode() {
//  return Objects.hash(id, name, description, price, categories, rating, imagenes, stock, characteristics, availability, isReserved);
// }

 public Product(String name, String description, Double price, Set<Category> categories, Double rating, Set<Imagen> imagenes, Integer stock, List<Characteristic> characteristics, ProductAvailability availability, boolean isReserved) {
  this.name = name;
  this.description = description;
  this.price = price;
  this.categories = new HashSet<>();
  this.rating = rating;
  this.imagenes = new HashSet<>();
  this.stock = stock;
  this.characteristics = new ArrayList<>();
//  this.availability = availability;
  this.isReserved = isReserved;
 }
}
