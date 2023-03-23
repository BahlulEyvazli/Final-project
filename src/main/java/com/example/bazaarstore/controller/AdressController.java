package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.AdressDTO;
import com.example.bazaarstore.model.entity.Adress;
import com.example.bazaarstore.service.AdressService;
import com.stripe.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AdressController {

    private final AdressService adressService;

    public AdressController(AdressService adressService) {
        this.adressService = adressService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody AdressDTO adressDTO){
        Adress adress = adressService.addAddres(adressDTO);
        return ResponseEntity.ok(adress);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAddress(){
        List<Adress> adressList = adressService.getAddresses();
        return ResponseEntity.ok(adressList);
    }
}
