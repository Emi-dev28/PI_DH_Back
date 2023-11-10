package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

public interface ICategoryService {

    Category categoryRegistry(Category category) throws Exception;

//     void deleteCategoryByName(String name);

    CategoryDto categoryRegistryWImg(Category category, MultipartFile multipartFile) throws Exception;

    void deleteCategoryById(Long id);


    Category findCategoryByName(String name);
}
