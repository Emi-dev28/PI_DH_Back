package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.ReserveDto;
import com.PI_back.pi_back.model.Reserve;
import com.PI_back.pi_back.services.IReservesService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReserveServiceimpl implements IReservesService {
    @Override
    public Set<ReserveDto> getReserves() {
        return null;
    }

    @Override
    public ReserveDto getOne(Long id) {
        return null;
    }

    @Override
    public ReserveDto registry(Reserve reserve) {
        return null;
    }

    @Override
    public ReserveDto update(Long id) {
        return null;
    }

    @Override
    public void deleteReserve(Long id) {

    }
}
