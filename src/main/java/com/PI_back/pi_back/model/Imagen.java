package com.PI_back.pi_back.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "IMAGENES")


public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "imagenUrl")

    private String imagenUrl;
    //@Column(name = "imagenId")

   // private String imagenId;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Producto producto;
//    @ManyToOne()
//    @JoinColumn(name = "files")




    public Imagen(String imagenUrl, Producto producto) {
        this.imagenUrl = imagenUrl;
        this.producto = producto;
    }

    public Imagen(String imagenUrl) {
        this.imagenUrl = imagenUrl;
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
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }

    @JsonBackReference
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
