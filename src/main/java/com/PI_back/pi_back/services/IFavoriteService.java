package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.FavoriteDto;
import com.PI_back.pi_back.model.Favorite;
import com.PI_back.pi_back.repository.FavoriteRepository;

import java.util.List;

public interface IFavoriteService {
    List<FavoriteDto> getAllFavs();

    FavoriteDto getOne(Long id);

    FavoriteDto registry(Favorite favorite);

    FavoriteDto update(Long id, Favorite favorite);

    void deleteFav(Long id);
}
