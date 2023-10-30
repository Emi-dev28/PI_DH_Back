package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> searchByEmail(@Param("email") String email);
}
