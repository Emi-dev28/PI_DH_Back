package com.PI_back.pi_back.services.impl;


import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.repository.CharacteristicRepository;
import com.PI_back.pi_back.services.ICharacteristic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class CharacteristicImpl implements ICharacteristic {

    @Autowired private CharacteristicRepository characteristicRepository;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProductoServiceImpl productoService;
    @Override
    public List<CharacteristicDto> listOfCharacteristics() {
        var list = characteristicRepository.findAll();
        List<CharacteristicDto> listOfCharDto = new ArrayList<CharacteristicDto>();
        for(Characteristic charac : list){
            listOfCharDto.add(objectMapper.convertValue(charac, CharacteristicDto.class));
        }

        return listOfCharDto;
    }

    @Override
    public CharacteristicDto registerACharacteristic(Characteristic characteristic, Long id) throws Exception {
        var prod = productoService.searchById(id);
//        characteristic.setProduct(objectMapper.convertValue(prod,Product.class));
        prod.getCharacteristics().add(characteristic); /* todo : resolver el tema del product_id en null */
        var savedChar = characteristicRepository.save(characteristic);
        productoService.updateById(prod.getId(),objectMapper.convertValue(prod, Product.class));
        var toSave = objectMapper.convertValue(savedChar, CharacteristicDto.class);
        return toSave;
    }
    @Override
    public CharacteristicDto registry(Characteristic characteristic){
        var savedCharacteristic = characteristicRepository.save(characteristic);
        return objectMapper.convertValue(savedCharacteristic, CharacteristicDto.class);
    }
    @Override
    public CharacteristicDto getOne(Long id) {
        var getOne = characteristicRepository.findById(id).get();
        var description = getOne.getDescription();
        var product = getOne.getProduct();
        return CharacteristicDto.builder().description(description).product(product).build();
    }

    @Override
    public CharacteristicDto updateById(Long id, Characteristic characteristic) {
        var characteristicToUpdate = characteristicRepository.findById(id).get();
        characteristicToUpdate.setId(characteristic.getId());
        characteristicToUpdate.setDescription(characteristic.getDescription());
        characteristicToUpdate.setProduct(characteristic.getProduct());
        characteristicRepository.save(characteristicToUpdate);
        CharacteristicDto dto = objectMapper.convertValue(characteristicToUpdate, CharacteristicDto.class);
        return dto;
    }

    @Override
    public void delete(Long id) {
    Characteristic toDelete = characteristicRepository.findById(id).get();
    characteristicRepository.delete(toDelete);
    }
}
