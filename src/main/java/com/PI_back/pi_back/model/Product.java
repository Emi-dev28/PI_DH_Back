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
    @JsonProperty("categories")
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
    @Nullable
    // @JsonProperty("set_of_characteristics")
    private List<Characteristic> characteristics;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "product_availability")
    @JsonProperty("product_availability")
    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductAvailability> availability;

    @Nullable
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "reserves")
    private Set<Reserve> reserves;

    @Nullable
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "favorites")
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


}
