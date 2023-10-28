package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Imagen;

import javax.transaction.Transactional;
import java.util.List;

public interface ImagenService {

    List<Imagen> listarImagen();

    void registrarImagen(Imagen imagen);

    @Transactional
    void deleteImagen(Long id);


}
