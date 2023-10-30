package com.PI_back.pi_back.security;

import com.PI_back.pi_back.services.impl.UserServiceImplement;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@ComponentScan("com.PI_back.pi_back.security")
// todo: si da error colocar :@ComponentScan() para que lo escanee de forma coorecta.

public class JwtAuthenticationFilter extends OncePerRequestFilter {

     private final JwtServiceImplement jwtServiceImplement;
     private final UserServiceImplement userService;

    @Autowired
    public JwtAuthenticationFilter(JwtServiceImplement jwtServiceImplement, UserServiceImplement userService) {
        this.jwtServiceImplement = jwtServiceImplement;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("api/v1/auth")){
            filterChain.doFilter(request,response);return;
        }
        final String authenticationHeader = request.getHeader("Authorization");
        String jwt;
        String username = "";
        if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        // Retorna el jwt, corta la primera parte que seria el "Bearer"
        jwt = authenticationHeader.substring(7);
        username = jwtServiceImplement.extractUsername(jwt);
        // todo ; para chequear que el usuario ya esta conectado o authenticado, esta el objeto SecurityContextHolder

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // todo: una vez que el usuario no esta conectado, lo que se hace es chequear el usuario de la base de datos
            UserDetails userDetails = this.userService.UserDetailsService().loadUserByUsername(username);
            if (jwtServiceImplement.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        // el WebAuthenticationDetails contiene informacion de la request actual
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request,response);
            }
        }
    }
}
