package com.PI_back.pi_back.services;

import com.PI_back.pi_back.model.Reserve;

import java.util.Set;

public interface IReservesService {

    Set<Reserve> getReserves(Long uid);

    Reserve addReserve(Long pid, Long uid);

    // TODO: id de la reserva y id del User
    void deleteReserve(Long id, Long uid);
}
