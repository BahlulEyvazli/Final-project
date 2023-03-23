package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.AdressDTO;
import com.example.bazaarstore.model.entity.Adress;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.AdressRepository;
import com.example.bazaarstore.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressService {

    private final AdressRepository adressRepository;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AdressService(AdressRepository adressRepository, JwtService jwtService, UserRepository userRepository) {
        this.adressRepository = adressRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public Adress addAddres(AdressDTO adressDTO){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Adress adress = Adress.builder().country(adressDTO.getCountry()).city(adressDTO.getCity())
                .street(adressDTO.getStreet()).postalCode(adressDTO.getPostalCode())
                .user(user).build();

        return adressRepository.save(adress);
    }

    public List<Adress> getAddresses(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Adress> adresses = adressRepository.findAllByUserId(user.getId()).orElseThrow();
        return adresses;
    }

}
