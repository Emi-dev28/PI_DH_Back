package com.PI_back.pi_back.utils;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.PI_back.pi_back.utils.Permission;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor

public enum Role {
    USER(Arrays.asList(Permission.READ_ALL_PRODUCTS, Permission.READ_ALL_CATEGORIES)),
    ADMIN(Arrays.asList(
            Permission.READ_ALL_PRODUCTS,
            Permission.SAVE_PRODUCT,
            Permission.SAVE_CATEGORY,
            Permission.GIVE_ADMIN,
            Permission.DELETE_PRODUCT,
            Permission.UPDATE_PRODUCT));

    private List<Permission> permission;


    public List<Permission> getPermissions() {
        return permission;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permission = permissions;
    }


}
