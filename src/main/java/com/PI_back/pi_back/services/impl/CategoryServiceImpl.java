package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.repository.CategoryRepository;
import com.PI_back.pi_back.services.ICategoryService;
import com.cloudinary.api.exceptions.BadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service

public class CategoryServiceImpl implements ICategoryService {
    //todo : actualizar el ICategoryService con los metodos


    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public Category categoryRegistry(Category category) throws Exception {
    logger.info("Se va a registrar una nueva categoria");
    var checkIfCategoryNameExist = repository.searchByName(category.getName()).isPresent();
    if(checkIfCategoryNameExist) {
        throw new Exception("el nombre de la categoria ya esta registrado");
    }
    repository.save(category);
    logger.info("Se registro la categoria {}", category);
        return category;
    }


    public void deleteCategoryById(Long id) {
    repository.deleteById(id);
    }

    public Set<Category> listAll() {
        Set<Category> categories = new HashSet<>();
        //noinspection ResultOfMethodCallIgnored
        repository.findAll().stream().map(categories::add);
        return categories;
    }
    public String findCategoryName(String name){
        return listAll()
                .stream()
                .filter(cat -> cat.getName().equals(name.toLowerCase())).toString();
    }

    public Category findById(Long id) throws BadRequest {

        var categoryToFind = repository.findById(id).orElse(null);

        if(categoryToFind!=null){
            return categoryToFind;
        }
        else{
            throw new BadRequest("Category not found");
        }


    }
    // todo --> metodo update();
}
