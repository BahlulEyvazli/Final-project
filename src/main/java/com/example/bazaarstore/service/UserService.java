package com.example.bazaarstore.service;


import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }
    public User getUserById(long id){
        if(userRepository.findById(id).isPresent()) {

            return userRepository.findUserById(id);
        }
        return null;
    }

}
