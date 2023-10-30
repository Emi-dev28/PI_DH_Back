package com.PI_back.pi_back.model;

import com.PI_back.pi_back.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@Builder
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
    @NotNull
    private boolean terms;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToMany(mappedBy = "user")
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
