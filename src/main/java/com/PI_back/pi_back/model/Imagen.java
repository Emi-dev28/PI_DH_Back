package com.PI_back.pi_back.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "IMAGENES")


public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen_url")

    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "producto_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Transient
    private Product product;
//    @ManyToOne()
//    @JoinColumn(name = "files")

    @OneToOne(mappedBy = "img")
    private Category category;



    public Imagen(String imageUrl, Product product) {
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public Imagen(String imagenUrl) {
        this.imageUrl = imagenUrl;
    }

    public Imagen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagenUrl() {
        return imageUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imageUrl = imagenUrl;
    }

    @JsonBackReference
    public Product getProducto() {
        return product;
    }

    @JsonBackReference
    public void setProducto(Product product) {
        this.product = product;
    }
}
