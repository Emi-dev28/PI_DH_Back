package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.dto.ImageDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.exceptions.ProductNotFoundException;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.repository.ProductAvailabilityRepository;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.services.IProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class ProductoServiceImpl implements IProductoService {

    private final Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private final CategoryServiceImpl categoryService;
    private final ProductoRepository productoRepository;
    private UploadServiceImplement uploadService;
    private ImagenServiceImpl imagenService;
    private ObjectMapper objectMapper;
    private CharacteristicImpl characteristicService;
    private ProductAvailabilityRepository availabilityRepository;

    @Autowired
    public ProductoServiceImpl(CategoryServiceImpl categoryService, ProductoRepository productoRepository, UploadServiceImplement uploadService, ImagenServiceImpl imagenService, ObjectMapper objectMapper, CharacteristicImpl characteristicService, ProductAvailabilityRepository productAvailabilityRepository) {
        this.categoryService = categoryService;
        this.productoRepository = productoRepository;
        this.uploadService = uploadService;
        this.imagenService = imagenService;
        this.objectMapper = objectMapper;
        this.characteristicService = characteristicService;
        this.availabilityRepository = productAvailabilityRepository;
    }


    // Registra productos, primero chequea si esta en la base de datos un producto con el mismo nombre,
    // luego lo registra
    @Override
    public ProductDto productRegistry(Product product) throws Exception {
        Logger.info("el nombre del producto a registrar es: {}", product.getName());
        var checkIfProductNameExists = productoRepository.searchByName(product.getName()).isPresent();
        if (checkIfProductNameExists) {
            Logger.info("El producto a designar con nombre {}, ya se encuentra registrado", product.getName());
            throw new Exception("El nombre del producto ingresado ya se encuentra registrado en la base de datos");
        } else {
            Product productToRegister = productoRepository.save(product);
            Set<ImageDto> imagesDtos = new HashSet<>();
            Set<CategoryDto> categoriesDto = new HashSet<>();
//            Set<Category> categories = new HashSet<>();
            ProductDto productoDto = objectMapper.convertValue(productToRegister, ProductDto.class);
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
        Product product = productoRepository.findById(id).get();
        Set<Imagen> imgs = product.getImagenes();
        for (Imagen img : imgs) {
            imagenService.deleteImagen(img.getId());
        }
        Set<Category> categories = product.getCategories();
        for (Category cat : categories) {
            categoryService.deleteCategoryById(cat.getId());
        }

        productoRepository.deleteById(id);
    }

    @Override
    public List<Product> listProduct() {
        return productoRepository.findAll();
    }

    public ProductDto registry(
            Product product) throws Exception {
        var registeredProd = productoRepository.save(product);
        Logger.info(registeredProd.getAvailability().toString());
        var id = registeredProd.getId();
        var name = registeredProd.getName();
        var description = registeredProd.getDescription();
        var price = registeredProd.getPrice();
        var availability = registeredProd.getAvailability();
        var isReserved = registeredProd.isReserved();
        var rating = registeredProd.getRating();
        var imagenes = registeredProd.getImagenes();
        var stock = registeredProd.getStock();
        var characteristics = productoRepository.findById(id).get().getCharacteristics();
        Logger.info("lista de caracteristicas: {}", characteristics);
        characteristics.forEach(characteristic -> {
            characteristic.setProduct(product);
            characteristicService.registry(characteristic);
        });
        var reserves = registeredProd.getReserves();
        var favorites = registeredProd.getFavorites();
        Logger.info("Esto es lo que recibe de disponibilidad", product.getAvailability());
        var prodToSave = ProductDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .images(imagenes)
                .stock(stock)
                .characteristics(characteristics)
                .availability(availability)
                .reserves(reserves)
                .favorites(favorites)
                .isReserved(isReserved)
                .build();
        return prodToSave;
    }

    @Override
    public Set<Imagen> addImage(
            List<MultipartFile> files
    ) throws IOException {
        Set<Imagen> imgs = new HashSet<>();
        for (MultipartFile multipartFile : files) {
            var urlImg = uploadService.uploadFile(multipartFile);
            var toAdd = Imagen.builder().imageUrl(urlImg).build();
            imgs.add(toAdd);
            imagenService.registrarImagen(toAdd);
        }
        return imgs;
    }

    @Override
    public ProductDto searchById(Long id) {
        Product productABuscar = productoRepository.findById(id).orElse(null);
        if (productABuscar != null) {
            Logger.info("Se encontro el producto con id {}", id);
        } else {
            Logger.info("El producto buscado con id {}, no se encuentra en la base de datos", id);
        }
        var response = objectMapper.convertValue(productABuscar, ProductDto.class);
        return response;
    }

    // recibe una category y un id (del producto donde va a guardar la categoria)
    @Override
    public CategoryDto asignCategoryToProduct(Category category, Long id) {
        var product = productoRepository.findById(id).get();
        product.getCategories().add(category);
        return objectMapper.convertValue(category, CategoryDto.class);

    }

    @Override
    public CharacteristicDto asignCharacteristic(Characteristic characteristic, Long id) {
        var prod = productoRepository.findById(id).get();
        prod.getCharacteristics().add(characteristic);
        var charactDto = objectMapper.convertValue(characteristic, CharacteristicDto.class);
        return charactDto;
    }


    @Override
    public ProductDto updateById(Long id, Product product) {
        Product prodToUpdate = productoRepository.findById(id).get();
        prodToUpdate.setName(product.getName());
        prodToUpdate.setDescription(product.getDescription());
        prodToUpdate.setPrice(product.getPrice());
        prodToUpdate.setRating(product.getRating());
        prodToUpdate.setStock(product.getStock());
//        prodToUpdate.setCharacteristics(product.getCharacteristics());
        productoRepository.save(prodToUpdate);
        ProductDto productDto = objectMapper.convertValue(prodToUpdate, ProductDto.class);
        return productDto;
    }

    @Override
    public ProductDto removeCategory(Long id, String name) {
        var prod = productoRepository.findById(id).get();
        var cat = categoryService.findCategoryByName(name);
        prod.getCategories().remove(cat);
        return objectMapper.convertValue(prod, ProductDto.class);
    }

    @Override
    public void asignCategoryToProduct(String productName, String categoryName) throws Exception {
        Product product = productoRepository.searchByName(productName.toLowerCase())
                .orElseThrow(() -> new ProductNotFoundException("El nombre del producto no se encontro en la base de datos"));
        Category category = categoryService.findByName(categoryName);
        product.getCategories().add(category);
        productoRepository.save(product);
    }

    // Recibe el nombre de la caracteristica y el id del producto al cual se le quiere añadir una caracteristica
    @Override
    public void asignCharacteristicToProduct(Characteristic characteristic, Long id) {
        Product product = productoRepository.findById(id).get();
        product.getCharacteristics().add(characteristic);
    }


    @Override
    public ProductDto getProductByName(String name) {
        var product = productoRepository.searchByName(name).get();

        return objectMapper.convertValue(product, ProductDto.class);
    }

    @Override
    public Set<Product> filterAvailabilityBetweenProducts(LocalDate from, LocalDate to, String name) throws JsonProcessingException {
            return productoRepository.findProductsByAvailabilityBetweenDatesAndName(from, to, name);
    }


}
