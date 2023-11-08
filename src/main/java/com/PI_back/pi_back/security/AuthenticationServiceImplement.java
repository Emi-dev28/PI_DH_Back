package com.PI_back.pi_back.security;

import com.PI_back.pi_back.dto.AuthenticationRequest;
import com.PI_back.pi_back.dto.AuthenticationResponse;
import com.PI_back.pi_back.dto.RegisterRequest;
import com.PI_back.pi_back.dto.UserDto;
import com.PI_back.pi_back.exceptions.UserAlreadyRegisteredException;
import com.PI_back.pi_back.model.Token;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.repository.TokenRepository;
import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.security.auth_Interfaces.IAuthenticationService;
import com.PI_back.pi_back.security.auth_Interfaces.JwtService;
import com.PI_back.pi_back.utils.Role;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public AuthenticationResponse login(AuthenticationRequest request) {
        // todo; el authenticate toma un objeto UsernamePasswordAuthenticationToken y recibe el username y password
        logger.info("Lo que llega de la request es {}", request);
        logger.info("el Username y la password con la que se va a armar el UsernamePasswordAuthenticationToken son {} {}", request.getUsername(),request.getPassword());


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        // todo; buscando el user

        var user = userRepository.searchByUsername(request.getUsername())
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
        logger.info("Se encuentro o no se encontro? {}", user);
        var jwtToken = jwtService.generateToken(user, generateExtraClaim(user));
        logger.info("Se genero el token {}", jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
      //  revokeAllUserTokens(user);
       // saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRol())
                .build();
    }

    private Map<String, Object> generateExtraClaim(User user) {
        Map<String, Object> extraClaims= new HashMap<>();
        extraClaims.put("name", user.getFirstname());
        extraClaims.put("role", user.getRol().name());
        extraClaims.put("permissions", user.getAuthorities());
        return extraClaims;
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
                .rol(Role.USER)
                .build();

        try {
            var userToFind = userRepository.searchByUsername(register.getUsername()).isPresent();
            if(userToFind) {
                logger.error("The user that you are trying to register is already in the db");
                throw new UserAlreadyRegisteredException("The user that you are trying to register is already in the db");
            }
        }catch(Exception e){
            logger.error("An error has been occur in the register method.");
        }
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user, generateExtraClaim(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRol())
                .build();
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
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username != null){
            var user = userRepository.searchByUsername(username)
                    .orElseThrow(() ->  new UsernameNotFoundException("User not found"));
            if(jwtService.isTokenValid(refreshToken, user)){
                var accesToken = jwtService.generateToken(user, generateExtraClaim(user));
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
