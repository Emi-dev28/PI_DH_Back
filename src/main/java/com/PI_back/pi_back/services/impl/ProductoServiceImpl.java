package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.ProductoDto;
import com.PI_back.pi_back.exceptions.NombreProductoYaExiste;
import com.PI_back.pi_back.model.Producto;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.services.IProductoService;
import com.PI_back.pi_back.services.ImagenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductoServiceImpl implements IProductoService {

    private final Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private final ProductoRepository productoRepository;
    private ImagenService imagenService;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }



    // Registra productos, primero chequea si esta en la base de datos un producto con el mismo nombre,
    // luego lo registra
    @Override
    public Producto registrarProducto(Producto producto) throws Exception {
        Logger.info("el nombre del producto a registrar es: {}", producto);
        if(productoRepository.buscarPorNombre(producto.getNombre()).isPresent()){
            Logger.info("El producto a designar con nombre '{}', ya se encuentra registrado", producto.getNombre());
            throw new Exception("El nombre del producto ingresado ya se encuentra registrado en la base de datos");
        }else{
            productoRepository.save(producto);
            Logger.info("Se ha registrado un nuevo producto {}", producto);


        }
        return producto;
    }



    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto buscarPorId(Long id) {
    Producto productoABuscar = productoRepository.findById(id).orElse(null);
    if(productoABuscar != null){
    Logger.info("Se encontro el producto con id {}", id);
    }
    else{
        Logger.info("El producto buscado con id {}, no se encuentra en la base de datos", id);
    }
    return productoABuscar;
    }
}
