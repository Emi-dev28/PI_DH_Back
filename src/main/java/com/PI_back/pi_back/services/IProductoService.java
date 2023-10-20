package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Producto;

import java.util.List;

public interface IProductoService {
    Producto registrarProducto(Producto producto);
    void eliminarProducto(Producto producto);
    List<Producto> listarProductos();

}
