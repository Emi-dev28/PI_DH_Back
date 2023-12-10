package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.ReserveDto;
import com.PI_back.pi_back.model.Reserve;

import java.util.Set;

public interface IReservesService {

    Set<ReserveDto> getReserves(Long id);


    ReserveDto addReserve(Reserve reserve, Long id);

    // TODO: id de la reserva y id del User
    void deleteReserve(Long id, Long uId);
}
