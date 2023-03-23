package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findProductBySku(String sku);

    Product findProductByIdAndActiveIsTrue(Long productId);

    List<Product> findAllByActiveIsTrue();

    List<Product> findAllByUserIdAndActiveIsTrue(Long userId);

    Product findByUserIdAndSku(Long userId,String sku);

}
