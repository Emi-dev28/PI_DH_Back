package com.PI_back.pi_back.controllers.auth;

import com.PI_back.pi_back.controllers.Product.ProductoController;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.services.impl.UserServiceImplement;
import com.PI_back.pi_back.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImplement userServiceImplement;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping
    public ResponseEntity<List<User>> listOfUsers(){
        return ResponseEntity.ok(userServiceImplement.listUsers());
    }

    @PutMapping("/update/{email}")
    public void updateUser(@PathVariable String email, @RequestBody Role rol){
        userServiceImplement.updateByEmail(email, rol);
    }
    @GetMapping("/reserves/{id}")
    public ResponseEntity<HashSet<Product>> listOfReserves(@PathVariable Long id) {return ResponseEntity.ok((userServiceImplement.listReserves(id)));}
    @GetMapping("/favorites/{id}")
    public ResponseEntity<HashSet<Product>> listOfFavorites(@PathVariable Long id) {return ResponseEntity.ok((userServiceImplement.listFavorites(id)));}
    @PostMapping("/add-reserve/{id}")
    public ResponseEntity<Boolean> addNewReserve(@PathVariable Long id, @RequestBody Product product){
        return ResponseEntity.ok(userServiceImplement.addReserve(id, product));
    }
    @PostMapping("/add-favorites/{id}")
    public ResponseEntity<Boolean> addNewfavorite(@PathVariable Long id, @RequestBody Product product){
        return ResponseEntity.ok(userServiceImplement.addFavorites(id, product));
    }
    @DeleteMapping("/delete-reserve/{id}/{p_id}")
    public void onDeleteReserve(@PathVariable Long id, @PathVariable Long p_id){
        userServiceImplement.deleteReserve(id, p_id);
    }

    @DeleteMapping("/delete-favorite/{id}/{p_id}")
    public void onDeleteFavorite(@PathVariable Long id, @PathVariable Long p_id){
        userServiceImplement.deleteFavorite(id, p_id);
    }
}
