package com.PI_back.pi_back.security;

import com.PI_back.pi_back.security.filter.JwtAuthenticationFilter;
import com.PI_back.pi_back.utils.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig extends WebSecurityConfiguration {


    private final JwtAuthenticationFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception {
        // csrf: Es una vulnerabilidad web, Cross Site Request Forgery. Se deshabilita porque nosotros usamos Jwts.
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                            auth.requestMatchers("/api/v1/auth/**").permitAll();
                            auth.requestMatchers("/api/v1/auth/**").permitAll();
                            auth.requestMatchers("/api/v1/auth/authenticate").permitAll();
                            auth.requestMatchers("/api/v1/productos").permitAll();
                            auth.requestMatchers("/api/v1/productos/registrar").permitAll();
                            auth.requestMatchers("/error").permitAll();
                            auth.requestMatchers("/api/v1/categorias/registrar").permitAll();
                            auth.requestMatchers("/api/v1/categorias").permitAll();
                            // todo: especificaciones de acceso segun url para manipular los Productos.
                            // para traer la lista de productos se requiere ser USER
                            auth.requestMatchers(HttpMethod.GET, "/api/v1/productos")
                                    .hasAuthority(Permission.READ_ALL_PRODUCTS.name());
                            // para registrar un producto se requiere ser admin
                            auth.requestMatchers(HttpMethod.POST, "/api/v1/productos/registrar")
                                    .hasAuthority(Permission.SAVE_PRODUCT.name());
                            auth.requestMatchers(HttpMethod.PUT, "/api/v1/productos/añadir-caracteristica/{id}")
                                            .hasAuthority(Permission.SAVE_PRODUCT.name());
                            auth.requestMatchers(HttpMethod.PUT, "/api/v1/productos/update/{id}")
                                            .hasAuthority(Permission.UPDATE_PRODUCT.name());
                            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/productos/eliminar/{id}")
                                            .hasAuthority(Permission.DELETE_PRODUCT.name());
                            auth.requestMatchers(HttpMethod.PUT, "/api/v1/productos/asignarle-categoria/{id}")
                                    .hasAuthority(Permission.SAVE_CATEGORY.name());
                            // para dar rol administrador a un user se requiere ser admin
                            // todo: especificaciones de acceso segun url para manipular el rol Admin.
                            auth.requestMatchers(HttpMethod.POST ,"api/v1/admin/give-admin")
                                    .hasAuthority(Permission.GIVE_ADMIN.name());
                            // todo: especificaciones de acceso segun url para manipular las categorias.
                            auth.requestMatchers(HttpMethod.GET, "/api/v1/categorias")
                                            .hasAuthority(Permission.READ_ALL_CATEGORIES.name());
                            auth.requestMatchers(HttpMethod.POST, "/api/v1/categories/registrar")
                                            .hasAuthority(Permission.SAVE_CATEGORY.name());
                            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/categories/eliminar/{id}")
                                            .hasAuthority(Permission.SAVE_CATEGORY.name());
                            auth.anyRequest().permitAll();
                        }
                        )
                .sessionManagement(session -> session
                        // la sesion no tiene estado
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)

                .logout(logout -> logout.logoutUrl("api/v1/auth/logout"));

        return httpSecurity.build();
    }
}
