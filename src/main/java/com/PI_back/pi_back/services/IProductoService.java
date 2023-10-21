package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Producto;

import java.util.List;

public interface IProductoService {
    void registrarProducto(Producto producto);
    void eliminarProducto(Long id);
    List<Producto> listarProductos();

}
