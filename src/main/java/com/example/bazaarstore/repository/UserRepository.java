package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndActiveIsTrue(String username);

}
