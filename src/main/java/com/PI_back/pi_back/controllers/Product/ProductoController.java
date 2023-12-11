package com.PI_back.pi_back.controllers.Product;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.*;
import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private UploadServiceImplement uploadServiceImplement;
    @Autowired
    private ImagenServiceImpl imagenService;
    @Autowired
    private ProductoServiceImpl productoService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public ResponseEntity<Set<com.PI_back.pi_back.model.Product>> listOfProducts(
            @RequestParam(name = "from",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(name = "to",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(name = "name",required = false) String name
    ) throws JsonProcessingException {
        logger.info("los productos a listar son: {}", productoService.listProduct());
        Set<Product> productList = new HashSet<>();
        if(from != null && to != null && name != null){
            var listFilteredProds = productoService.filterAvailabilityBetweenProducts(from, to, name);
            productList.addAll(listFilteredProds);
        }else{
            ObjectMapper objectMapper = new ObjectMapper();

        }
        return ResponseEntity.ok(productList);
    }

    // Trae producto por nombre. Para el Buscador.
    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productoService.getProductByName(name));
    }

    // todo: fijarse por que el arreglo de strings 'characteristics' se persiste a la base de datos como binary
    @PostMapping(value = "/registrar-producto")
    public ResponseEntity<ProductDto> registrarUnproducto(
            //@RequestParam("product") String product,
            @RequestParam/*(value="product" ,required = true)*/ Product product) throws Exception {
//            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {
        /*ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);*/
        /*TypeReference<Product> typeReference = new TypeReference<Product>(){};*/
        //var productObj =  objectMapper.readValue(product, typeReference);
        logger.info("Caracteristicas: {}", product.getCharacteristics());
        logger.info("prod {}", product);
        var prod = productoService.registry(product);
        return ResponseEntity.ok(prod);
    }

    @PostMapping("/registrar-prod-sin-img")
    public ResponseEntity<ProductDto> registrarUnProdSinImg(
            @RequestBody Product product
    ) throws Exception {

        return ResponseEntity.ok(productoService.registry(product));
    }

    // registra una imagen a un producto
    @PostMapping(value = "/imagen/{id}")
    public ResponseEntity<Set<Imagen>> imgRegistry(
            @ModelAttribute List<MultipartFile> files,
            @RequestParam Long id
    ) throws IOException {
        return ResponseEntity.ok(productoService.addImage(files));
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productoService.deleteProduct(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> searchById(@PathVariable Long id) {
        var producto = productoService.searchById(id);
        ResponseEntity<ProductDto> respuesta;
        if (producto != null) {
            respuesta = ResponseEntity.ok(producto);
        } else {
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    // Edita todos los campos, menos las caracteristicas y las imagenes
    @PutMapping("/editar/{id}")
    public ProductDto updateById(@PathVariable Long id, @RequestBody Product product) {
        return productoService.updateById(id, product);
    }

    @PutMapping("/añadir-caracteristica/{id}")
    public void setCharacteristic(@PathVariable Long id,
                                  @RequestBody Characteristic characteristic) {
        var product = productoService.searchById(id);
        product.getCharacteristics().add(characteristic);
        logger.info("Se le añadio la caracteristica {}, a el producto {}", characteristic, product);
    }

    @PostMapping("/asignar-categoria/{id}")
    public ResponseEntity<CategoryDto> asignCategoryToProduct(@RequestBody Category category, @PathVariable Long id) {
        return ResponseEntity.ok(productoService.asignCategoryToProduct(category, id));
    }

    @PutMapping("/asignarle-caracteristica/{id}")
    public void asignCharacteristic(@RequestBody Characteristic characteristic, @PathVariable Long id) {
        productoService.asignCharacteristicToProduct(characteristic, id);
    }
    @GetMapping("/filtrar-por-fechas")
    public ResponseEntity<Set<Product>> filterProdsByDates(
            @RequestParam(name = "from",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(name = "to",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(name = "name",required = false) String name
            ) throws JsonProcessingException {
        var response = productoService.filterAvailabilityBetweenProducts(from, to, name);
        logger.info(response.toString());
        return ResponseEntity.ok(response);
    }
}
