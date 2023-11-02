package com.PI_back.pi_back.security.auth_Interfaces;


import com.PI_back.pi_back.dto.AuthenticationRequest;
import com.PI_back.pi_back.dto.AuthenticationResponse;
import com.PI_back.pi_back.dto.RegisterRequest;
import com.PI_back.pi_back.dto.UserDto;

import java.util.List;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest register);
    List<UserDto> authenticatedUsers();
}
