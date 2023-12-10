package com.PI_back.pi_back.services.impl;


import com.PI_back.pi_back.dto.FavoriteDto;
import com.PI_back.pi_back.model.Favorite;
import com.PI_back.pi_back.services.IFavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements IFavoriteService {
    @Override
    public List<FavoriteDto> getAllFavs() {
        return null;
    }

    @Override
    public FavoriteDto getOne(Long id) {
        return null;
    }

    @Override
    public FavoriteDto registry(Favorite favorite) {
        return null;
    }

    @Override
    public FavoriteDto update(Long id, Favorite favorite) {
        return null;
    }

    @Override
    public void deleteFav(Long id) {

    }
}
