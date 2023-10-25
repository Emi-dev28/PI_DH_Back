package com.PI_back.pi_back.controllers;

import com.PI_back.pi_back.dto.ProductoDto;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Producto;
import com.PI_back.pi_back.services.impl.ImagenServiceImpl;
import com.PI_back.pi_back.services.impl.ProductoServiceImpl;
import com.PI_back.pi_back.services.impl.UploadServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        logger.info("los productos a listar son: {}", productoService.listarProductos());
        return ResponseEntity.ok(productoService.listarProductos());
    }


    // todo: resolver el id de las imagenes que se muestran en null.
    @PostMapping("/registrar")
    public ResponseEntity<Producto> registrarProducto(ProductoDto producto, List<MultipartFile> multipartFiles){
        try{
            Producto producto1 = new Producto(producto.getNombre(),producto.getDescripcion(),null, producto.getStock());
            List<Imagen> listaDeImagenes = new ArrayList<>();
            for(MultipartFile imagen : multipartFiles){
                var urlImg = uploadServiceImplement.uploadFile(imagen);
                Imagen imagen1 = new Imagen(urlImg,producto1);
                listaDeImagenes.add(imagen1);
            }
            producto1.setImagenes(listaDeImagenes);
            logger.info("El producto a guardar es" + producto1);
            productoService.registrarProducto(producto1);
            return ResponseEntity.ok(producto1);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("No se pudo registrar el producto");
        }
        return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable Long id){
    productoService.eliminarProducto(id);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id){
        var producto = productoService.buscarPorId(id);
        ResponseEntity<Producto> respuesta;
        if(producto != null){
            respuesta = ResponseEntity.ok(producto);
        }
        else{
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }
}
