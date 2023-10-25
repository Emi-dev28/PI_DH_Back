package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.ProductoDto;
import com.PI_back.pi_back.model.Producto;

import java.util.List;

public interface IProductoService {
    Producto registrarProducto(Producto producto) throws Exception;
    void eliminarProducto(Long id);
    List<Producto> listarProductos();

    Producto buscarPorId(Long id);


}
