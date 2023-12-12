package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Favorite;

import java.util.Set;

public interface IFavoriteService {
    Set<Favorite> getFavs(Long uid);

    Favorite addFav(Long pid , Long uid);

    void deleteFav(Long id, Long uid);
}
