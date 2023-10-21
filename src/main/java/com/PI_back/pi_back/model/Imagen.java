package com.PI_back.pi_back.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "IMAGENES")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre")
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String imagenId;
    private MultipartFile file;


}
