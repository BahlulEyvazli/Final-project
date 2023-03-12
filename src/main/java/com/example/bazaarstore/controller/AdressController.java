package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.AdressDTO;
import com.example.bazaarstore.model.entity.Adress;
import com.example.bazaarstore.service.AdressService;
import com.stripe.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bazaar/address")
public class AdressController {

    private final AdressService adressService;

    public AdressController(AdressService adressService) {
        this.adressService = adressService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody AdressDTO adressDTO, @RequestParam("token") String token){
        Adress adress = adressService.addAddres(adressDTO,token);
        return ResponseEntity.ok(adress);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAddress(@RequestParam("token") String token){
        List<Adress> adressList = adressService.getAddresses(token);
        return ResponseEntity.ok(adressList);
    }
}
