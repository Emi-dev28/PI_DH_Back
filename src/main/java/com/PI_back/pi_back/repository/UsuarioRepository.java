package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
