package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.ImageDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.exceptions.ProductNotFoundException;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.services.IProductoService;
import com.PI_back.pi_back.services.ImagenService;
import com.PI_back.pi_back.services.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class ProductoServiceImpl implements IProductoService {

    private final Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private final CategoryServiceImpl categoryService;
    private final ProductoRepository productoRepository;
    private UploadServiceImplement uploadService;
    private ImagenService imagenService;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductoServiceImpl(CategoryServiceImpl categoryService, ProductoRepository productoRepository, UploadServiceImplement uploadService, ImagenService imagenService, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.productoRepository = productoRepository;
        this.uploadService = uploadService;
        this.imagenService = imagenService;
        this.objectMapper = objectMapper;
    }




    // Registra productos, primero chequea si esta en la base de datos un producto con el mismo nombre,
    // luego lo registra
    @Override
    public ProductDto productRegistry(Product product) throws Exception {
        Logger.info("el nombre del producto a registrar es: {}", product.getName());
        var checkIfProductNameExists = productoRepository.searchByName(product.getName()).isPresent();
        if(checkIfProductNameExists){
            Logger.info("El producto a designar con nombre {}, ya se encuentra registrado", product.getName());
            throw new Exception("El nombre del producto ingresado ya se encuentra registrado en la base de datos");
        }
        else{
            Product productToRegister = productoRepository.save(product);
            Set<ImageDto> imagesDtos = new HashSet<>();
            Set<CategoryDto> categoriesDto = new HashSet<>();
//            Set<Category> categories = new HashSet<>();
            ProductDto productoDto  = objectMapper.convertValue(productToRegister, ProductDto.class);
            for (Category category : productToRegister.getCategories()) {
               CategoryDto categoryDto = objectMapper.convertValue(category, CategoryDto.class);
               categoriesDto.add(categoryDto);
            }
            for (Imagen images : productToRegister.getImagenes()) {
                ImageDto imageDto = objectMapper.convertValue(images, ImageDto.class);
                imagesDtos.add(imageDto);
            }
//            productoDto.setImages(imagesDtos);
////            productoDto.setCategories(categoriesDto);
//            productoDto.setImages(imagesDtos);
//            productoDto.setCategories(categoryDtos);
            Logger.info("Se ha registrado un nuevo producto {}", product);

            return productoDto;


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

    public ProductDto registry(Product product,
                               List<MultipartFile> files) throws Exception {
        var name = product.getName();
        var description = product.getDescription();
        var price = product.getPrice();
        var categories = product.getCategories();
        var rating = product.getRating();
        var imagenes = product.getImagenes();
        for (MultipartFile multipartFile : files){
            var urlImg = uploadService.uploadFile(multipartFile);
            var toAdd = Imagen.builder().imageUrl(urlImg).product(product).build();
            imagenes.add(toAdd);
            imagenService.registrarImagen(toAdd);
        }
        for (Category category : categories){
            var newCategory = new Category(category.getName());
            categoryService.categoryRegistry(newCategory);
            categories.add(newCategory);
        }
        var stock = product.getStock();
        var characteristics = product.getCharacteristics();
        var prodToSave = ProductDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .categories(categories)
                .images(imagenes)
                .stock(stock)
                .characteristics(characteristics)
                .build();

        return prodToSave;
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

    @Override
    public void updateById(Long id, Product product) {
        var list = listProduct();
        for(int i = 0; i < list.size() ;i++ ){
          Product p = list.get(i);
          if(p.getId().equals(id)){
              list.set(i, product);
              return;
          }
        }
    }
    @Override
    public void asignCategoryToProduct(String productName, String categoryName) throws Exception {
        Product product = productoRepository.searchByName(productName.toLowerCase())
                .orElseThrow(() -> new ProductNotFoundException("El nombre del producto no se encontro en la base de datos"));
        Category category = categoryService.findByName(categoryName);
        product.getCategories().add(category);
        productoRepository.save(product);
    }
}
