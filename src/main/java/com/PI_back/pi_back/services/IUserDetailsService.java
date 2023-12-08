package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.utils.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public interface IUserDetailsService {
    UserDetailsService UserDetailsService();
    List<User> listUsers();
    //void updateByEmail(String email, Role rol);
    void updateByEmail(String email, Role rol);
    HashSet<Product> listReserves(Long id);
    HashSet<Product> listFavorites(Long id);
    Boolean addReserve(Long id, Product product);
    Boolean addFavorites(Long id, Product product);
    void deleteReserve(Long id, Long p_id);
    void deleteFavorite(Long id, Long p_id);



}
