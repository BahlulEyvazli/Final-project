package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList,Long> {

}
