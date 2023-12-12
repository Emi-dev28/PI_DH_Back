package com.PI_back.pi_back.services.impl;


import com.PI_back.pi_back.model.Favorite;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.repository.FavoriteRepository;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.services.IFavoriteService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FavoriteServiceImpl implements IFavoriteService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;
    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, ProductoRepository productoRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Set<Favorite> getFavs(Long uid) {
        User user = userRepository.findById(uid).get();
        return user.getFavorites();
    }

    @Override
    public Favorite addFav(Long pid, Long uid) {
        User user = userRepository.findById(uid).get();
        Product product = productoRepository.findById(pid).get();
        var newFav = Favorite.builder().user(user).product(product).build();
        assert user.getFavorites() != null;
        user.getFavorites().add(newFav);
        return newFav;
    }

    @Override
    public void deleteFav(Long pid, Long uid) {
        User user = userRepository.findById(uid).get();
        assert user.getFavorites() != null;
        user.getFavorites().remove(productoRepository.findById(pid));
    }
}
