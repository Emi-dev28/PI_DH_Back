package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.AvailabilityDto;
import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Product;

import java.util.List;

public interface IProductoService {
    ProductDto productRegistry(Product productDto) throws Exception;
    void deleteProduct(Long id);
    List<Product> listProduct();
    ProductDto searchById(Long id);

    ProductDto removeCategory(Long id, String name);

    void asignCategoryToProduct(String productName, String categoryName) throws Exception;


    // recibe una category y un id (del producto donde va a guardar la categoria)
    CategoryDto asignCategoryToProduct(Category category, Long id);

    CharacteristicDto asignCharacteristic(Characteristic characteristic, Long id);

    ProductDto updateById(Long id, Product product);

    // Recibe el nombre de la caracteristica y el id del producto al cual se le quiere añadir una caracteristica
    void asignCharacteristicToProduct(Characteristic characteristic, Long id);

//    AvailabilityDto getAvailability(Long id);

    ProductDto getProductByName(String name);
}
