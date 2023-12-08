package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.controllers.Product.ProductoController;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.services.IUserDetailsService;

import com.PI_back.pi_back.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Component()
@ComponentScan("com.PI_back.pi_back.services.impl.UserServiceImplement")
public class UserServiceImplement implements IUserDetailsService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Override
    public UserDetailsService UserDetailsService() {
        return username -> userRepository.searchByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
    }
    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateByEmail(String email, Role rol) {
        userRepository.searchByEmail(email).ifPresent(u -> u.setRol(rol));
    }

    @Override
    public HashSet<Product> listReserves(Long id) {
        Optional<User> user = userRepository.findById(id);
        return (HashSet<Product>) user.orElseThrow().getReserves();
    }

    @Override
    public HashSet<Product> listFavorites(Long id) {
        Optional<User> user = userRepository.findById(id);
        return (HashSet<Product>) user.orElseThrow().getFavorites();
    }

    @Override
    public Boolean addReserve(Long id, Product product) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getReserves().add(product);
    }
    @Override
    public Boolean addFavorites(Long id, Product product) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getFavorites().add(product);
    }

    @Override
    public void deleteReserve(Long id, Long p_id) {
        Optional<User> user = userRepository.findById(id);
        user.get().getReserves().removeIf(product -> (Objects.equals(product.getId(), p_id)) );
    }

    @Override
    public void deleteFavorite(Long id, Long p_id) {
        Optional<User> user = userRepository.findById(id);
        user.get().getFavorites().removeIf(product -> (Objects.equals(product.getId(), p_id)) );
    }
}
