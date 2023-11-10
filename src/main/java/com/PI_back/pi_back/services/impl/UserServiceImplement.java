package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.controllers.Product.ProductoController;
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

import java.util.List;

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
/*
    @Override
    public void updateByEmail(String email, Role rol) {
        userRepository.changeRole(rol,email);
        logger.info("Éxito");
    }
*/

    @Override
    public void updateByEmail(String email, Role rol) {
        userRepository.searchByEmail(email).ifPresent(u -> u.setRol(rol));
        ;
    }
}
