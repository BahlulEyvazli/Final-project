package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndActiveIsTrue(String username);
    User findUserById(long id);

}
