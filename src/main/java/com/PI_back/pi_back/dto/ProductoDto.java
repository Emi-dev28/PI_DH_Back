package com.PI_back.pi_back.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDto {

    // todo: chequear notaciones en entidades, services y repository.
    private String nombre;
    private String descripcion;
    private String stock;

}
