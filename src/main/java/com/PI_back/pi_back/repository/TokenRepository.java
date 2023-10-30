package com.PI_back.pi_back.repository;

import com.PI_back.pi_back.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository  extends JpaRepository<Token, Long> {

    @Query(value = """
      SELECT t from Token t INNER JOIN User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);


}
