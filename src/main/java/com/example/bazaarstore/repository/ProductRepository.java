package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {


    // bunu ozumnen yazmagimin sebebi springin ozudne olan Optional ile getirir mense istemirem ozum servicde
    // null gelerse elnen handle edirem.
    Product findProductById(long id);
}
