package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Product;

import java.util.List;

public interface IProductoService {
    ProductDto productRegistry(Product productDto) throws Exception;
    void deleteProduct(Long id);
    List<Product> listProduct();
    Product searchById(Long id);

    void asignCategoryToProduct(String productName, String categoryName) throws Exception;

    void updateById(Long id, Product product);
}
