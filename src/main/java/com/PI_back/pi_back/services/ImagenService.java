package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Imagen;

import java.util.List;

public interface ImagenService {

    List<Imagen> listarImagen();

    void registrarImagen(Imagen imagen);

    void deleteImagen(Long id);


}
