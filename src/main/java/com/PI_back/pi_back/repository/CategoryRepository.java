package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("DELETE c FROM Category c where c.name = :name")
    Category deleteByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Optional<Product> searchByName(@Param("name") String name);
}
