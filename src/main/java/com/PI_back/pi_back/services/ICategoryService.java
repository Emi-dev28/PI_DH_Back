package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Category;

public interface ICategoryService {

    Category categoryRegistry(Category category) throws Exception;

//     void deleteCategoryByName(String name);

    void deleteCategoryById(Long id);
}
