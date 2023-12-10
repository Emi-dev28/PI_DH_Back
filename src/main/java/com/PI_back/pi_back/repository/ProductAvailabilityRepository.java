package com.PI_back.pi_back.repository;
import com.PI_back.pi_back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<com.PI_back.pi_back.model.ProductAvailability, Long> {


//    @Query("SELECT p FROM Product p WHERE p.availability.fromDate >= :from AND p.availability.toDate <= :to")

}

