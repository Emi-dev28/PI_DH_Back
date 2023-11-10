package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.dto.CharacteristicDto;
import com.PI_back.pi_back.model.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {


}
