package com.PI_back.pi_back.security.filter;

import com.PI_back.pi_back.security.JwtServiceImplement;
import com.PI_back.pi_back.services.impl.UserServiceImplement;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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

     private JwtServiceImplement jwtServiceImplement;
     private UserServiceImplement userService;

    @Autowired
    public JwtAuthenticationFilter(JwtServiceImplement jwtServiceImplement, UserServiceImplement userService) {
        this.jwtServiceImplement = jwtServiceImplement;
        this.userService = userService;
    }

    @Override // Se asegura que una peticion solo sea procesada una sola vez. Si es repetida, no la procesa.
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {


        // 1.- Obtener el header;
        final String authenticationHeader = request.getHeader("Authorization");
        // 2.- Obtener el jwt del header;
        // 3.- Obtener el subject/username desde el jwt;
        String jwt;
        String username;
        if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authenticationHeader.substring(7);
        username = jwtServiceImplement.extractUsername(jwt);
        // 4. Settear un objeto Authentication dentro del Security context holder.

        // Retorna el jwt, corta la primera parte que seria el "Bearer"

        // todo ; para chequear que el usuario ya esta conectado o authenticado, esta el objeto SecurityContextHolder

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // todo: una vez que el usuario no esta conectado, lo que se hace es chequear el usuario de la base de datos
            UserDetails userDetails = userService.UserDetailsService().loadUserByUsername(username);
            if (jwtServiceImplement.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
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
