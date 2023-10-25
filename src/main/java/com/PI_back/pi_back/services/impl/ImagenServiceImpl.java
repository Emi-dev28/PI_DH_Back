package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.repository.ImagenRepository;
import com.PI_back.pi_back.services.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenServiceImpl implements ImagenService {


    private final ImagenRepository imagenRepository;

    @Autowired
    public ImagenServiceImpl(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    @Override
    public List<Imagen> listarImagen() {
        return null;
    }

    @Override
    public Imagen registrarImagen(Imagen imagen) {
        return  imagenRepository.save(imagen);
    }
}
