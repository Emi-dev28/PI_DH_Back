package com.PI_back.pi_back.controllers.Product;

import com.PI_back.pi_back.dto.AvailabilityDto;
import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import com.PI_back.pi_back.utils.LocalDateAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.lang.reflect.Type;
import java.time.LocalDate;
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

    // Trae producto por nombre. Para el Buscador.
    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String name){
       return ResponseEntity.ok(productoService.getProductByName(name));
    }

    // todo: fijarse por que el arreglo de strings 'characteristics' se persiste a la base de datos como binary
    @PostMapping(value = "/registrar-producto" )
    public ResponseEntity<ProductDto> registrarUnproducto(
            //@RequestParam("product") String product,
            @RequestBody/*(value="product" ,required = true)*/ String product,
            @RequestBody/*(value = "files", required = false)*/ List<MultipartFile> files) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        TypeReference<Product> typeReference = new TypeReference<Product>(){};
        //var productObj =  objectMapper.readValue(product, typeReference);
        var produ = objectMapper.readValue(product, Product.class);
        logger.info("Caracteristicas: {}", produ.getCharacteristics());
        logger.info("prod {}", produ);
        var prod = productoService.registry(produ, files);
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

    @PostMapping("/asignar-categoria/{id}")
    public ResponseEntity<CategoryDto> asignCategoryToProduct(@RequestBody Category category, @PathVariable Long id){
        return ResponseEntity.ok(productoService.asignCategoryToProduct(category, id));
    }
    @PutMapping("/asignarle-caracteristica/{id}")
    public void asignCharacteristic(@RequestBody Characteristic characteristic, @PathVariable Long id){
        productoService.asignCharacteristicToProduct(characteristic,id);
    }

//    @GetMapping("/disponibilidad/{id}")
//    public ResponseEntity<AvailabilityDto> getAvailability(@PathVariable Long id){
//        return ResponseEntity.ok(productoService.getAvailability(id));
//    }
}
