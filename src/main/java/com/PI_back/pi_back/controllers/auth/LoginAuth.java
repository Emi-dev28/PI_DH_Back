package com.PI_back.pi_back.controllers.auth;

import com.PI_back.pi_back.dto.AuthenticationRequest;
import com.PI_back.pi_back.dto.AuthenticationResponse;
import com.PI_back.pi_back.dto.RegisterRequest;
import com.PI_back.pi_back.security.AuthenticationServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/")
public class LoginAuth {

    @Autowired
    private final AuthenticationServiceImplement authenticationServiceImplement;

    @Autowired
    public LoginAuth(AuthenticationServiceImplement authenticationServiceImplement) {
        this.authenticationServiceImplement = authenticationServiceImplement;
    }


    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> registry(
            @RequestBody RegisterRequest registerRequest
    ){
        return ResponseEntity.ok(authenticationServiceImplement.register(registerRequest));
    }
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authRequest
            ){
        return ResponseEntity.ok(authenticationServiceImplement.login(authRequest));
    }
    @PostMapping("refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(authenticationServiceImplement.refreshToken(request,response));
    }
}
