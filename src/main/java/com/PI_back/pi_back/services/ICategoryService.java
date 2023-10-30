package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Category;

public interface ICategoryService {

    void categoryRegistry(Category category) throws Exception;

    void deleteCategoryByName(String name);

}
