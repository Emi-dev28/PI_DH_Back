package com.PI_back.pi_back.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    @NotNull
    private String firstname;

    @Column(name = "lastname")
    @NotNull
    private String lastname;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name= "email")
    @NotNull
    private String email;

    @Column(name = "terms")
    @NotNull
    private boolean terms;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToMany(mappedBy = "usuario")
    private Set<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(rol.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
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
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
