package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.utils.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserDetailsService {
    UserDetailsService UserDetailsService();
    List<User> listUsers();
    //void updateByEmail(String email, Role rol);
    void updateByEmail(String email, Role rol);
}
