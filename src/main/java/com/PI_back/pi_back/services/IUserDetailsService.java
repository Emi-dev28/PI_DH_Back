package com.PI_back.pi_back.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserDetailsService {
    UserDetailsService UserDetailsService();
}
