package com.PI_back.pi_back.controllers;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

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


    // todo: resolver el id de las imagenes que se muestran en null.
    @PostMapping("/registrar")
    public ResponseEntity<ProductDto> productsRegistry(
            @RequestBody @Valid Product producto,
            @ModelAttribute List<MultipartFile> multipartFiles){
        try {
            // mediante el producto, nos llega el dto, que contiene nombre, descripcion y stock. Se crea un Producto para persistir en la base de datos
            // Se crea una lista de imagenes para luego añadirle objetos de tipo imagen

//            Product product1 = new Product(producto.getName(), producto.getDescription(),
//                    producto.getPrice(),producto.getCategory(),null, null,producto.getStock(),producto.getCharacteristics());

//            Product producto1 = new Product(producto.getName(), producto.getDescription(),producto.getPrice(), producto.getCategories(), null, null,producto.getStock(),producto.getCharacteristics());
//            for(MultipartFile imagen : multipartFiles){
//                var urlImg = uploadServiceImplement.uploadFile(imagen);
//                Imagen imagen1 = new Imagen(urlImg, productoDto);
//                listaDeImagenes.add(imagen1);
//                imagenService.registrarImagen(imagen1);
//            }
            for(MultipartFile file : multipartFiles){
                var imgUrl = uploadServiceImplement.uploadFile(file);
                Imagen img = new Imagen(imgUrl, producto);
                producto.getImagenes().add(img);
            }

            ResponseEntity<ProductDto> response;
            ProductDto productoDto = productoService.productRegistry(producto);
            if (productoDto != null) {
                return response = ResponseEntity.status(HttpStatus.CREATED).build();
            } else response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return response;
        }   catch(Exception e){
            e.printStackTrace();
            logger.error("product could not be registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//            Set<Imagen> listaDeImagenes = new HashSet<>();

//            producto1.setImagenes(listaDeImagenes);
//            logger.info("El producto a guardar es" + producto1);
//            productoService.productRegistry(producto1);
//            return ResponseEntity.status(HttpStatus.CREATED).body(producto1);
//        }   catch(Exception e){
//            e.printStackTrace();
//            logger.error("No se pudo registrar el producto");
//        }
//        return ResponseEntity.badRequest().build();
    }

    // todo: fijarse por que el arreglo de strings 'characteristics' se persiste a la base de datos como binary
    @PostMapping(value = "/registrar-producto", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ProductDto> registrarUnproducto(
             @RequestParam("product") String product,
             @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var productObj =  objectMapper.readValue(product, Product.class);
        var prod = productoService.registry(productObj, files);
        return ResponseEntity.ok(prod);
    }
    @DeleteMapping("/eliminar/{id}")
    public void deleteProduct(@PathVariable Long id){
        var productoABuscar = productoService.searchById(id);
        var imagenesAEliminar = productoABuscar.getImagenes();
        for (Imagen img : imagenesAEliminar) {
            imagenService.deleteImagen(img.getId());
        }
        productoService.deleteProduct(id);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Product> searchById(@PathVariable Long id){
        var producto = productoService.searchById(id);
        ResponseEntity<com.PI_back.pi_back.model.Product> respuesta;
        if(producto != null){
            respuesta = ResponseEntity.ok(producto);
        }
        else{
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    @PutMapping("/update/{id}")
    public void updateById(@PathVariable Long id, @RequestBody com.PI_back.pi_back.model.Product product){
        productoService.updateById(id,product);
    ;
    }
    @PutMapping("/añadir-caracteristica/{id}")
    public void setCharacteristic(@PathVariable Long id, String characteristic){
        var product = productoService.searchById(id);
        product.getCharacteristics().add(characteristic);
        logger.info("Se le añadio la caracteristica {}, a el producto {}", characteristic, product);
    }
    @PutMapping("/asignarle-categoria/{id}")
    public ResponseEntity asignCategory(@PathVariable Long id, CategoryDto categoryDto ){
        String catName = categoryService.findCategoryName(categoryDto.getName());
        if(catName != null) {
            com.PI_back.pi_back.model.Product product = productoService.searchById(id);
            Category category = Category.builder().name(catName).build();
            product.getCategories().add(category);
            logger.info("Se le añadio la categoria {} al producto {}",category, product);
            return ResponseEntity.ok(category);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
