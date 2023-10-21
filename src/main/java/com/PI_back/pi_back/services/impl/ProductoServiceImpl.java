package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.exceptions.NombreProductoYaExiste;
import com.PI_back.pi_back.model.Producto;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.services.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {
    private final Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    @Override
    public void registrarProducto(Producto producto) {
        if(productoRepository.buscarPorNombre(producto.getNombre()).isPresent()){
            Logger.info("El producto a designar con nombre '{}', ya se encuentra registrado", producto.getNombre());
            throw new NombreProductoYaExiste("El producto con nombre {}, ya se encuentra registrado en la base de datos", producto.getNombre());
        }else{
            productoRepository.save(producto);
            Logger.info("Se ha registrado un nuevo producto {}", producto);
        }
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
}
