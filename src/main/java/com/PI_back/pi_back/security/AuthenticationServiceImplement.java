package com.PI_back.pi_back.security;

import com.PI_back.pi_back.dto.AuthenticationRequest;
import com.PI_back.pi_back.dto.AuthenticationResponse;
import com.PI_back.pi_back.dto.RegisterRequest;
import com.PI_back.pi_back.dto.UserDto;
import com.PI_back.pi_back.model.Token;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.repository.TokenRepository;
import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.security.auth_Interfaces.IAuthenticationService;
import com.PI_back.pi_back.security.auth_Interfaces.JwtService;
import com.PI_back.pi_back.utils.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@Data
@Builder
@RequiredArgsConstructor
public class AuthenticationServiceImplement implements IAuthenticationService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplement.class);


    private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;

    private TokenRepository tokenRepository;

    @Autowired
    public AuthenticationServiceImplement(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // todo; el authenticate toma un objeto UsernamePasswordAuthenticationToken y recibe el username y password
        logger.info("Lo que llega de la request es {}", request);
        logger.info("el Username y la password con la que se va a armar el UsernamePasswordAuthenticationToken son {} {}", request.getEmail(),request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        // todo; buscando el user
        var user = userRepository.searchByEmail(request.getEmail())
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
        logger.info("Se encuentro o no se encontro? {}", user);
        var jwtToken = jwtService.generateToken(user);
        logger.info("Se genero el token {}", jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken).refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())return;
        validUserTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest register) {
        User user = User
                .builder()
                .firstname(register.getName())
                .lastname(register.getLastname())
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .email(register.getEmail())
                .terms(register.isTerms())
                .rol(register.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public List<UserDto> authenticatedUsers() {
        var users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> new UserDto(
                        user.getFirstname(),
                        user.getLastname(),
                        user.getUsername(),
                        user.getFirstname() + " " + user.getLastname()))
                .toList();
                logger.info("lista de usuarios ya authenticados {}", userDtos);
        return userDtos;
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = userRepository.searchByEmail(userEmail)
                    .orElseThrow(() ->  new UsernameNotFoundException("User not found"));
            if(jwtService.isTokenValid(refreshToken, user)){
                var accesToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accesToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accesToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
