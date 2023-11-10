package com.PI_back.pi_back.controllers.Product;


import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.services.impl.CharacteristicImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producto/caracteristicas")
public class CharacteristicController {
    @Autowired private CharacteristicImpl service;


    @GetMapping("/{id}")
    public ResponseEntity<List<CharacteristicDto>> getCharacteristics(@PathVariable Long id){
        var characts = service.listOfCharacteristics();
        return ResponseEntity.ok(characts);
    }
    @PostMapping("/registrar-caracteristica/{id}")
    public ResponseEntity<CharacteristicDto> characteristicRegistry(@RequestBody Characteristic characteristic, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(service.registerACharacteristic(characteristic,id));
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CharacteristicDto> updateCharacteristic(@PathVariable Long id, Characteristic characteristic){
    return ResponseEntity.ok(service.updateById(id, characteristic));
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
    @GetMapping
    public ResponseEntity<List<CharacteristicDto>> listOfCharacteristics(){
        return ResponseEntity.ok(service.listOfCharacteristics());
    }
}
