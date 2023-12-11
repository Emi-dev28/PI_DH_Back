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

import java.util.Set;

@Service
public class ReserveServiceimpl implements IReservesService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private ReservesRepository reservesRepository;
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

    @Override
    public Reserve addReserve(Long pid, Long uid) {
        User user = userRepository.findById(uid).get();
        Product product = productoRepository.findById(pid).get();
        Reserve newRes = Reserve.builder().build();
        assert user.getReserves() != null;
        user.getReserves().add(newRes);
        return newRes;
    }

    @Override
    public void deleteReserve(Long pid, Long uid) {
        User user = userRepository.findById(uid).get();
        assert user.getReserves() != null;
        user.getReserves().remove(productoRepository.findById(pid));
    }
}
