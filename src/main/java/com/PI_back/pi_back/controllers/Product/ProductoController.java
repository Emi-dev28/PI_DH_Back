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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ResponseEntity<List<com.PI_back.pi_back.model.Product>> listOfProducts(){
        logger.info("los productos a listar son: {}", productoService.listProduct());
        return ResponseEntity.ok(productoService.listProduct());
    }


    // todo: fijarse por que el arreglo de strings 'characteristics' se persiste a la base de datos como binary
    @PostMapping(value = "/registrar-producto", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ProductDto> registrarUnproducto(
             @RequestParam("product") String product,
             @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        com.fasterxml.jackson.core.type.TypeReference<Product> typeReference = new TypeReference<Product>(){};
        var productObj =  objectMapper.readValue(product, typeReference);
        var prod = productoService.registry(productObj, files);
        return ResponseEntity.ok(prod);
    }
    @DeleteMapping("/eliminar/{id}")
    public void deleteProduct(@PathVariable Long id){
        productoService.deleteProduct(id);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> searchById(@PathVariable Long id){
        var producto = productoService.searchById(id);
        ResponseEntity<ProductDto> respuesta;
        if(producto != null){
            respuesta = ResponseEntity.ok(producto);
        }
        else{
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    // Edita todos los campos, menos las caracteristicas y las imagenes
    @PutMapping("/editar/{id}")
    public ProductDto updateById(@PathVariable Long id, @RequestBody Product product){
        return productoService.updateById(id,product);
    }
    @PutMapping("/añadir-caracteristica/{id}")
    public void setCharacteristic(@PathVariable Long id,
                                  @RequestBody Characteristic characteristic){
        var product = productoService.searchById(id);
        product.getCharacteristics().add(characteristic);
        logger.info("Se le añadio la caracteristica {}, a el producto {}", characteristic, product);
    }
//    @PutMapping("/asignarle-categoria/{id}")
//    public ResponseEntity asignCategory(@PathVariable Long id, CategoryDto categoryDto ){
//        String catName = categoryService.findCategoryName(categoryDto.getName());
//        if(catName != null) {
//            com.PI_back.pi_back.model.Product product = productoService.searchById(id);
//            Category category = Category.builder().name(catName).build();
//            product.getCategories().add(category);
//            logger.info("Se le añadio la categoria {} al producto {}",category, product);
//            return ResponseEntity.ok(category);
//        }
//        else{
//            return ResponseEntity.badRequest().build();
//        }
//    }
    @PutMapping("/asignarle-caracteristica/{id}")
    public void asignCharacteristic(@RequestBody Characteristic characteristic, @PathVariable Long id){
        productoService.asignCharacteristicToProduct(characteristic,id);
    }
//    @GetMapping("/characteristics/{id}")
//    public ResponseEntity<List<String>> listOfCharacteristics(){
//
//    return
//    }
    @PostMapping("/asignar-categoria/{id}")
    public ResponseEntity<CategoryDto> asignCategoryToProduct(@RequestBody Category category, @PathVariable Long id){
        return ResponseEntity.ok(productoService.asignCategoryToProduct(category, id));
    }

}
