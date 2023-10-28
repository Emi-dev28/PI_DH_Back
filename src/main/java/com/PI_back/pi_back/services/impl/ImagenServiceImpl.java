package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.repository.ImagenRepository;
import com.PI_back.pi_back.services.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return imagenRepository.findAll();
    }

    @Override
    public void registrarImagen(Imagen imagen) {
         imagenRepository.save(imagen);
    }

    @Transactional
    @Override
    public void deleteImagen(Long id) {
        imagenRepository.deleteById(id);
    }
}
