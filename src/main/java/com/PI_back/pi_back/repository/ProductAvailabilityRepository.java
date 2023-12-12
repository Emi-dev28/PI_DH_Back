package com.PI_back.pi_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<com.PI_back.pi_back.model.ProductAvailability, Long> {


//    @Query("SELECT p FROM Product p WHERE p.availability.fromDate >= :from AND p.availability.toDate <= :to")

}

