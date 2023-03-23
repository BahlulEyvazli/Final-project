package com.example.bazaarstore.repository;

import java.util.List;
import java.util.Optional;

import com.example.bazaarstore.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join User u on t.user.id=u.id where u.id=?1 and t.expired=false or t.revoked=false ")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
