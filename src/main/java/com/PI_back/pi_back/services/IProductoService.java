package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Product;

import java.util.List;

public interface IProductoService {
    void productRegistry(Product product) throws Exception;
    void deleteProduct(Long id);
    List<Product> listProduct();

    Product searchById(Long id);


}
