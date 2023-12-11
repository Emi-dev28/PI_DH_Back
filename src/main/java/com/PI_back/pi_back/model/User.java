package com.PI_back.pi_back.model;

import com.PI_back.pi_back.utils.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "firstname")
    @NotNull
    private String firstname;

    @NotBlank
    @Size(max = 50)
    @Column(name = "lastname")
    @NotNull
    private String lastname;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Size(max = 50)
    @Column(name = "username")
    private String username;

    @Column(name= "email")
    @NotBlank
    @Size(max = 120)
    private String email;

    @Column(name = "terms")
    private boolean terms;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "reserves")
    @JoinColumn(name = "user_id")
    private Set<Reserve> reserves;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "favorites")
    @JoinColumn(name = "user_id")
    private Set<Favorite> favorites;


    // todo: descomentar las autoridades, eliminar/modificar de la db los usuarios que tienen el rol en null, chequear que el register y el login.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = rol.getPermissions()
//                .stream().map(permission -> new SimpleGrantedAuthority(permission.name()))
//                .collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("Role_" + rol.name()));
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}