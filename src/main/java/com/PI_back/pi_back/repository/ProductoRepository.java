package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {


    @Query("SELECT p FROM PRODUCTO p WHERE p.Nombre = :nombre")
    Optional<Producto> buscarPorNombre(@Param("nombre") String nombre);


}
