package com.PI_back.pi_back.controllers;

import com.PI_back.pi_back.dto.ProductoDto;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public ResponseEntity<List<Product>> listOfProducts(){
        logger.info("los productos a listar son: {}", productoService.listProduct());
        return ResponseEntity.ok(productoService.listProduct());
    }


    // todo: resolver el id de las imagenes que se muestran en null.
    @PostMapping("/registrar")
    public ResponseEntity<Product> productsRegistry(ProductoDto producto, List<MultipartFile> multipartFiles){
        try{
            // mediante el producto, nos llega el dto, que contiene nombre, descripcion y stock. Se crea un Producto para persistir en la base de datos
            // Se crea una lista de imagenes para luego añadirle objetos de tipo imagen
            Product product1 = new Product(producto.getName(),producto.getDescription(), producto.getPrice(),producto.getQuantity(), producto.getCategory(), 0.0,null , producto.getStock());
            Set<Imagen> listaDeImagenes = new HashSet<>();
            for(MultipartFile imagen : multipartFiles){
                var urlImg = uploadServiceImplement.uploadFile(imagen);
                Imagen imagen1 = new Imagen(urlImg, product1);
                listaDeImagenes.add(imagen1);
                imagenService.registrarImagen(imagen1);
            }
            product1.setImagenes(listaDeImagenes);
            logger.info("El producto a guardar es" + product1);
            productoService.productRegistry(product1);
            return ResponseEntity.ok(product1);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("No se pudo registrar el producto");
        }
        return ResponseEntity.badRequest().build();
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
    public ResponseEntity<Product> buscarPorId(@PathVariable Long id){
        var producto = productoService.searchById(id);
        ResponseEntity<Product> respuesta;
        if(producto != null){
            respuesta = ResponseEntity.ok(producto);
        }
        else{
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }
}
