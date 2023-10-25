package com.PI_back.pi_back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Table(name = "Producto")
@Entity
public class Producto {
   // todo: mappear las relaciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripción")
    private String descripcion;
    @Column(name = "Image")
   // @OneToMany(mappedBy = "file")
    @OneToMany(mappedBy = "producto")
    @JsonManagedReference
    private List<Imagen> imagenes;
    @Column(name = "stock")
    private String stock;

    public Producto(Long id, String nombre, String descripcion, List<Imagen> imagenes, String stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
        this.stock = stock;
    }

    public Producto(String nombre, String descripcion, List<Imagen> imagenes, String stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
        this.stock = stock;
    }
    public Producto(String nombre, String descripcion, String stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
    }


    public Producto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonManagedReference
    public List<Imagen> getImagenes() {
        return imagenes;
    }

    @JsonManagedReference
    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
