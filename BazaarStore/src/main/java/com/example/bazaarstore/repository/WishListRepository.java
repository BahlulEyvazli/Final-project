package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUser(User user);

    List<WishList> findAllByUserAndAndProductIsTrue(User user);
    Optional<WishList> findWishListByProductIdAndUserId(Long productId,Long userId);

    @Query("SELECT w.product FROM WishList w WHERE w.user = :user AND w.product.active = true")
    List<Product> findActiveProductsByUser(@Param("user") User user);
}
