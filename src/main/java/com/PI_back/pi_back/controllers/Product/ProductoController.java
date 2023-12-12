package com.PI_back.pi_back.controllers.Product;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<List<ProductDto>> listOfProducts(

    ) throws JsonProcessingException {
        logger.info("los productos a listar son: {}", productoService.listProduct());
        return ResponseEntity.ok(productoService.listProduct());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductDto> getOneProd(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.searchById(id));
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

    @PostMapping("/registrar-imagen")
    public ResponseEntity<Set<Imagen>> registrarImagen(
            @ModelAttribute List<MultipartFile> files
    ) throws IOException {
        return ResponseEntity.ok(productoService.addImage(files));
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
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(name = "name", required = false) String name
    ) throws JsonProcessingException {
        var response = productoService.filterAvailabilityBetweenProducts(from, to, name);
        logger.info(response.toString());
        return ResponseEntity.ok(response);
    }
}
