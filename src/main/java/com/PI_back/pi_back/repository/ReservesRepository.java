package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservesRepository extends JpaRepository<Reserve,Long> {
}
