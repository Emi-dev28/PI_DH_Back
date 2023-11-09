package com.PI_back.pi_back.controllers;


import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.model.Category;

import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.cloudinary.api.exceptions.BadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/categorias")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @GetMapping()
    public ResponseEntity<List<Category>> listAllCategories(){
        return ResponseEntity.ok(categoryService.listAll());
    }
    @PostMapping("/registrar")
    // todo:
    public ResponseEntity<CategoryDto> catRegistry(
            @RequestParam("category") String category,
            @RequestParam(value = "file", required = false) MultipartFile img
    )   throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var catToRegis = objectMapper.readValue(category, Category.class);
        logger.info("Este es el nombre que queda luego del object mapper {}", catToRegis.getName());
        var cat = categoryService.categoryRegistryWImg(catToRegis, img);
        logger.info("Se va a registrar la siguiente categoria {}, si no cumple con las condiciones, el problema esta en la forma en que se guardan en la db", cat);
        return ResponseEntity.ok(cat);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id)  {
        categoryService.deleteCategoryById(id);
    return ResponseEntity.ok("Categoria eliminada Exitosamente");
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ){
        logger.info("Se va a editar la siguiente categoria {}", category);
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok().build();
    }




}
