package com.PI_back.pi_back.controllers;


import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.repository.CategoryRepository;
import com.PI_back.pi_back.services.impl.CategoryServiceImpl;
import com.cloudinary.api.exceptions.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/categorias")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping()
    public ResponseEntity<Set<Category>> listAllCategories(){
        return ResponseEntity.ok(categoryService.listAll());
    }
    @PostMapping("/registrar")
    public ResponseEntity<String> catRegistry(CategoryDto category, MultipartFile img) throws Exception {
        Category categoryToRegistry = categoryService.categoryRegistry(
                Category.builder().name(category.getName()).productList(null)
                .build());
        return ResponseEntity.ok("Categoria registrada correctamente");
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id)  {
        categoryService.deleteCategoryById(id);
    return ResponseEntity.ok("Categoria eliminada Exitosamente");
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Category> updateCatById(@PathVariable Long id , Category category) throws Exception {
//       // todo -->
//        return ResponseEntity;
//    }


}
