package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.Reserve;
import com.PI_back.pi_back.model.User;
import com.PI_back.pi_back.repository.ProductoRepository;
import com.PI_back.pi_back.repository.ReservesRepository;
import com.PI_back.pi_back.repository.UserRepository;
import com.PI_back.pi_back.services.IReservesService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class ReserveServiceimpl implements IReservesService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private final ReservesRepository reservesRepository;
    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public ReserveServiceimpl(ReservesRepository reservesRepository, UserRepository userRepository, ProductoRepository productoRepository) {
        this.reservesRepository = reservesRepository;
        this.userRepository = userRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Set<Reserve> getReserves(Long uid) {
        User user = userRepository.findById(uid).get();
        return user.getReserves();
    }

    /*
    @Override
    public Reserve addReserve(Long pid, Long uid) {
        User user = userRepository.findById(uid).get();
        Product product = productoRepository.findById(pid).get();
        var newRes = Reserve.builder().user(user).product(product).build();
        assert user.getReserves() != null;
        user.getReserves().add(newRes);
        return newRes;
    }
    */

    @Override
    public Reserve addReserve(Long pid, Long uid) {
        Optional<User> userOptional = userRepository.findById(uid);
        Optional<Product> productOptional = productoRepository.findById(pid);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();

            Reserve newRes = Reserve.builder().user(user).product(product).build();

            if (user.getReserves() == null) {
                user.setReserves(new HashSet<>());
            }

            user.getReserves().add(newRes);

            return newRes;
        } else {
            // Handle the case where either the user or product is not found
            // For example, you could throw an exception or return a default value.
            throw new NoSuchElementException("User or Product not found");
        }
    }

    @Override
    public void deleteReserve(Long reserveId) {
        Optional<Reserve> reserveOptional = reservesRepository.findById(reserveId);

        if (reserveOptional.isPresent()) {
            Reserve reserveToDelete = reserveOptional.get();
            User user = reserveToDelete.getUser();

            if (user != null && user.getReserves() != null) {
                user.getReserves().remove(reserveToDelete);
                reservesRepository.delete(reserveToDelete);
            } else {
                // Handle the case where user or reserves are null
                throw new NoSuchElementException("User or Reserves not found");
            }
        } else {
            // Handle the case where the reserve is not found
            throw new NoSuchElementException("Reserve not found");
        }
    }

}
