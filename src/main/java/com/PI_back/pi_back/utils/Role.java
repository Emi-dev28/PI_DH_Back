package com.PI_back.pi_back.utils;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

public enum Role {
    USER(Arrays.asList(Permissions.READ_ALL_PRODUCTS, Permissions.READ_ALL_CATEGORIES)),
    AMIN(Arrays.asList(
            Permissions.READ_ALL_PRODUCTS,
            Permissions.SAVE_PRODUCT,
            Permissions.SAVE_CATEGORY,
            Permissions.GIVE_ADMIN,
            Permissions.DELETE_PRODUCT,
            Permissions.UPDATE_PRODUCT));

    private List<Permissions> permissions;


    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
