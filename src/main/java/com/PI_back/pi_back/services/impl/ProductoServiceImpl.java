package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Product;
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
    public void productRegistry(Product product) throws Exception {
        Logger.info("el nombre del producto a registrar es: {}", product);
        if(productoRepository.searchByName(product.getName()).isPresent()){
            Logger.info("El producto a designar con nombre '{}', ya se encuentra registrado", product.getName());
            throw new Exception("El nombre del producto ingresado ya se encuentra registrado en la base de datos");
        }else{
            productoRepository.save(product);
            Logger.info("Se ha registrado un nuevo producto {}", product);


        }
    }



    @Override
    public void deleteProduct(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Product> listProduct() {
        return productoRepository.findAll();
    }

    @Override
    public Product searchById(Long id) {
    Product productABuscar = productoRepository.findById(id).orElse(null);
    if(productABuscar != null){
    Logger.info("Se encontro el producto con id {}", id);
    }
    else{
        Logger.info("El producto buscado con id {}, no se encuentra en la base de datos", id);
    }
    return productABuscar;
    }
}
