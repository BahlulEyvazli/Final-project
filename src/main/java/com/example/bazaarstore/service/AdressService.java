package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.AdressDTO;
import com.example.bazaarstore.model.entity.Adress;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.AdressRepository;
import com.example.bazaarstore.repository.UserRepository;
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


    public Adress addAddres(AdressDTO adressDTO,String token){
        User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
        Adress adress = Adress.builder().country(adressDTO.getCountry()).city(adressDTO.getCity())
                .street(adressDTO.getStreet()).postalCode(adressDTO.getPostalCode())
                .user(user).build();

        return adressRepository.save(adress);
    }

    public List<Adress> getAddresses(String token){
        User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
        List<Adress> adresses = adressRepository.findAllByUserId(user.getId()).orElseThrow();
        return adresses;
    }

}
