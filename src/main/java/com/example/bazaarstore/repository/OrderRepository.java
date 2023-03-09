package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
