package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> searchByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> searchByEmail(@Param("email") String email);



    //@Query("UPDATE User u SET u.rol = :newRole WHERE u.email = :emailParam")
    //void changeRole(@Param("newRole") Role newRole, @Param("emailParam") String email);

}
