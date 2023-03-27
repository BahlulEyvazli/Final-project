package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.model.entity.CartItem;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByCartAndStatusIsTrue(Cart cart);
}
