package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Product;

import java.util.List;

public interface IProductoService {
    ProductDto productRegistry(Product productDto) throws Exception;
    void deleteProduct(Long id);
    List<Product> listProduct();
    Product searchById(Long id);
<<<<<<< HEAD

    void asignCategoryToProduct(String productName, String categoryName) throws Exception;



=======
>>>>>>> 1d53e45b9abc70219436588aabb28aed07c29379
    void updateById(Long id, Product product);
}
