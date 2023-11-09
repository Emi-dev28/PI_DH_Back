package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.repository.CategoryRepository;
import com.PI_back.pi_back.services.ICategoryService;
import com.PI_back.pi_back.services.UploadService;
import com.cloudinary.api.exceptions.BadRequest;
import com.cloudinary.api.exceptions.NotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;


@Service

public class CategoryServiceImpl implements ICategoryService {
    //todo : actualizar el ICategoryService con los metodos


    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired private  CategoryRepository repository;
    @Autowired private UploadService uploadService;
    @Autowired private ObjectMapper objectMapper;


    @Override
    public Category categoryRegistry(Category category) throws Exception {
        logger.info("Se va a registrar una nueva categoria {}", category);
        var checkIfPresent = repository.searchByName(category.getName()).isPresent();
        if(checkIfPresent){
            logger.error("La nombre de la categoria que se intenta registrar ya se encuentra en base de datos");
            throw new Exception("La nombre de la categoria que se intenta registrar ya se encuentra en base de datos");
        }
        else{
            return repository.save(category);
        }
    }
    @Override
    public CategoryDto categoryRegistryWImg(
            Category category,
            MultipartFile multipartFile) throws Exception {
        logger.info("Se va a registrar una nueva categoria {}", category);
        var checkIfPresent = repository.searchByName(category.getName()).isPresent();
        if(checkIfPresent){
            logger.error("La nombre de la categoria que se intenta registrar ya se encuentra en base de datos");
            throw new Exception("La nombre de la categoria que se intenta registrar ya se encuentra en base de datos");
        }
        else{
            var savedCat = repository.save(category);
            var catName = savedCat.getName();
            var catDescrip = savedCat.getDescription();
            var urlImg = uploadService.uploadFile(multipartFile);
            var catImg = Imagen.builder().imageUrl(urlImg).category(category).build();

            var categoryDto = CategoryDto.builder()
                    .name(catName)
                    .description(catDescrip)
                    .img(catImg).build();
            return categoryDto;
        }
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
                .filter(cat -> cat.equals(name.toLowerCase())).toString();
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

    public Category findByName(String categoryName) throws NotFound {
        var cat = repository.searchByName(categoryName).orElseThrow(
                () -> new NotFound("Resource not found")
        );
        return cat;
    }
    // todo --> metodo update();
}
