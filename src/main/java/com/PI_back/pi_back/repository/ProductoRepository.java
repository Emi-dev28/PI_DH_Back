package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    @Transactional
    Optional<Producto> buscarPorNombre(@Param("nombre") String nombre);


}
