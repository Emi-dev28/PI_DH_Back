package com.PI_back.pi_back.controllers.auth;

import com.PI_back.pi_back.controllers.Product.ProductoController;
import com.PI_back.pi_back.model.Favorite;
import com.PI_back.pi_back.model.Reserve;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.services.impl.FavoriteServiceImpl;
import com.PI_back.pi_back.services.impl.ReserveServiceimpl;
import com.PI_back.pi_back.services.impl.UserServiceImplement;
import com.PI_back.pi_back.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceImplement userServiceImplement;
    @Autowired
    private ReserveServiceimpl reserveServiceimpl;
    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public ResponseEntity<List<User>> listOfUsers(){
        return ResponseEntity.ok(userServiceImplement.listUsers());
    }

    @PutMapping("/update/{email}/{rol}")
    public void updateUser(@PathVariable String email, @PathVariable Role rol){
        userServiceImplement.updateByEmail(email, rol);
        ResponseEntity.ok();
    }
    @GetMapping("/reserves/{id}")
    public ResponseEntity<Set<Reserve>> listOfReserves(@PathVariable Long id) {return ResponseEntity.ok((reserveServiceimpl.getReserves(id)));}
    @GetMapping("/favorites/{id}")
    public ResponseEntity<Set<Favorite>> listOfFavorites(@PathVariable Long id) {return ResponseEntity.ok((favoriteServiceImpl.getFavs(id)));}
    @PostMapping("/add-reserve/{uid}/{pid}")
    public ResponseEntity<Reserve> addNewReserve(@PathVariable Long uid, @PathVariable Long pid ){
        return ResponseEntity.ok(reserveServiceimpl.addReserve(uid, pid));
    }
    @PostMapping("/add-favorites/{uid}/{pid}")
    public ResponseEntity<Favorite> addNewfavorite(@PathVariable Long uid, @PathVariable Long pid ){
        return ResponseEntity.ok(favoriteServiceImpl.addFav(uid, pid));
    }
    @DeleteMapping("/delete-reserve/{reserveId}")
    public void onDeleteReserve(@PathVariable Long reserveId ){
        reserveServiceimpl.deleteReserve(reserveId);
    }

    @DeleteMapping("/delete-favorite/{uid}/{pid}")
    public void onDeleteFavorite(@PathVariable Long uid, @PathVariable Long pid ){
        favoriteServiceImpl.deleteFav(uid, pid);
    }

}
