package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.AvailabilityDto;

public interface IProductAvailabilityService {

    // Id de la disponibilidad del producto
    AvailabilityDto getAvailability(Long id);
}
