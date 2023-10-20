package com.PI_back.pi_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCTO")
@Entity
public class Producto {
    //Producto.
    //id: Long
    //(Si el nombre ya existe en la base de datos no se deberia guardar)
    //nombre: String
    //descripcion: String
    //imagen: cloudnary{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripción")
    private String descripcion;
    @Column(name = "Image")
    private Imagen Image;


}
