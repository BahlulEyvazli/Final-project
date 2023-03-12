package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdressRepository extends JpaRepository<Adress,Long> {

    Optional<List<Adress>> findAllByUserId(Long userId);
}
