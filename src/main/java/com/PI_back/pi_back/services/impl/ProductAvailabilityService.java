package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.dto.AvailabilityDto;
import com.PI_back.pi_back.repository.ProductAvailabilityRepository;
import com.PI_back.pi_back.services.IProductAvailabilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductAvailabilityService implements IProductAvailabilityService {

    private ProductAvailabilityRepository repository;
    private ProductoServiceImpl productoService;
    private ObjectMapper objectMapper;
    @Override
    public AvailabilityDto getAvailability(Long id) {
        var prod = productoService.searchById(id);
        return objectMapper.convertValue(prod.getAvailability(), AvailabilityDto.class);
    }

}
