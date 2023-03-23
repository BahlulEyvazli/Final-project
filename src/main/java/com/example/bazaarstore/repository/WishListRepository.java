package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUser(User user);


    Optional<WishList> findWishListByProductIdAndUserId(Long productId,Long userId);
}
