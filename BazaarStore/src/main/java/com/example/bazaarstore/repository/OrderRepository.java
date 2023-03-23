package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Order;
import com.example.bazaarstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //findAllByUserOrderByCreatedDateDesc

    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
