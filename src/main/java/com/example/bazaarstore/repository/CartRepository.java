package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cart> deleteByUser(User user);

}
