package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface ProductoRepository extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<Product> searchByName(@Param("name") String name);

//    @Query("SELECT p.characteristics FROM Product")
//    Optional<String> findCharacteristics();

}
