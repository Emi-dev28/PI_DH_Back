package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.services.IUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component()
@ComponentScan("com.PI_back.pi_back.services.impl.UserServiceImplement")
public class UserServiceImplement implements IUserDetailsService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetailsService UserDetailsService() {
        return username -> userRepository.searchByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
    }
}
