package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.CategoryDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Imagen;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.repository.CategoryRepository;
import com.PI_back.pi_back.repository.ImagenRepository;
import com.PI_back.pi_back.services.ICategoryService;
import com.PI_back.pi_back.services.UploadService;
import com.cloudinary.api.exceptions.NotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service

public class CategoryServiceImpl implements ICategoryService {
    //todo : actualizar el ICategoryService con los metodos


    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired private  CategoryRepository repository;
    @Autowired private ImagenRepository imagenRepository;
    @Autowired private UploadService uploadService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ImagenServiceImpl imagenService;
    // Sin imagen
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
    //Con imagen

    // todo: resolver pq no llega el imagen_id y el category_id
    @Override
    public CategoryDto categoryRegistryWImg(
            Category category,
            MultipartFile multipartFile) throws Exception {
//        logger.info("Se va a registrar una nueva categoria {}", category);
        var urlImg = uploadService.uploadFile(multipartFile);
        var img = Imagen.builder().imageUrl(urlImg).category(category).build();
        var registeredImg = imagenRepository.save(img);
        category.setImg(registeredImg);

        logger.info("Aca esta el id de la categoria {}", category.getId());
        var savedCat = repository.save(category);
        var catId = savedCat.getId();
        var catName = savedCat.getName();
        var catDescrip = savedCat.getDescription();
        var catImg = savedCat.getImg();
            var categoryDto = CategoryDto.builder()
                    .id(catId)
                    .name(catName)
                    .description(catDescrip)
                    .products(category.getProducts())
                    .urlImg(catImg.getImageUrl())
                    .build();
            return categoryDto;

        }


    // delete
    public void deleteCategoryById(Long id) {
    repository.deleteById(id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return repository.searchByName(name).get();
    }

    // lista las categorias
    public List<CategoryDto> listAll() {
//        Set<Category> categories = new HashSet<Category>(repository.findAll());
        //noinspection ResultOfMethodCallIgnored
        List<CategoryDto> categoryDtos = new ArrayList<>();
        repository.findAll().forEach(cat -> {
            categoryDtos.add(objectMapper.convertValue(cat, CategoryDto.class)
);});
        return categoryDtos ;
    }
    public String findCategoryName(String name){
        return listAll()
                .stream()
                .filter(cat -> cat.getName().equals(name.toLowerCase())).toString();
    }


    public Category findByName(String categoryName) throws NotFound {
        var cat = repository.searchByName(categoryName).orElseThrow(
                () -> new NotFound("Resource not found")
        );
        return cat;
    }

    public CategoryDto updateCategory(Long id, Category category) {
        Category categoryToUpdate = repository.findById(id).get();
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        categoryToUpdate.setProducts(category.getProducts());
        categoryToUpdate.setImg(category.getImg());
        repository.save(categoryToUpdate);
        CategoryDto categoryDto = objectMapper.convertValue(categoryToUpdate, CategoryDto.class);
        return categoryDto;
    }

    public CategoryDto findCategoryById(Long id) {
        return objectMapper.convertValue(repository.findById(id), CategoryDto.class);
    }

    public CategoryDto getById(Long id) {
        var cat = repository.findById(id).get();
        return objectMapper.convertValue(cat, CategoryDto.class);
    }

    public void asignProdToCat(Long id, Product product){
        var cat = getById(id);
        cat.setProducts(new HashSet<>());
        cat.getProducts().add(product);
        updateCategory(product.getId(),objectMapper.convertValue(cat, Category.class));
    }
    // todo --> metodo update();
}
