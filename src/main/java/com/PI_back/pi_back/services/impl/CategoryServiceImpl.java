package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.repository.CategoryRepository;
import com.PI_back.pi_back.services.ICategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class CategoryServiceImpl implements ICategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository repository;



    @Override
    public void categoryRegistry(Category category) throws Exception {
    logger.info("Se va a registrar una nueva categoria");
    var checkIfCategoryNameExist = repository.searchByName(category.getName()).isPresent();
    if(checkIfCategoryNameExist) {
        throw new Exception("el nombre de la categoria ya esta registrado");
    }
    repository.save(category);
    }

    @Override
    public void deleteCategoryByName(String name) {
    repository.deleteByName(name);
    }
}
