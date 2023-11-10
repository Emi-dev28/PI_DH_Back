package com.PI_back.pi_back.services;

import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.model.Characteristic;

import java.util.List;

public interface ICharacteristic {

    List<CharacteristicDto> listOfCharacteristics();

    CharacteristicDto registerACharacteristic(Characteristic characteristic, Long id) throws Exception;

    CharacteristicDto registry(Characteristic characteristic);

    CharacteristicDto getOne(Long id);

    CharacteristicDto updateById(Long id, Characteristic characteristic);

    void delete(Long id);
}
