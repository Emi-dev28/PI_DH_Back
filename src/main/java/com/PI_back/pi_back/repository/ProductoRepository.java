package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductoRepository extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<Product> searchByName(@Param("name") String name);

//    @Query("SELECT p.characteristics FROM Product")
//    Optional<String> findCharacteristics();
@Query("SELECT DISTINCT p FROM Product p JOIN FETCH p.availability av " +
        "WHERE (:from IS NULL OR av.fromDate >= :from) " +
        "AND (:to IS NULL OR av.toDate <= :to) " +
        "AND (:name IS NULL OR p.name LIKE %:name%)")
Set<Product> findProductsByAvailabilityBetweenDatesAndName(
        @Param("from") LocalDate from,
        @Param("to") LocalDate to,
        @Param("name") String name);

}
