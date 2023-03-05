package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
