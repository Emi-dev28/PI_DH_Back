package com.PI_back.pi_back.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

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
