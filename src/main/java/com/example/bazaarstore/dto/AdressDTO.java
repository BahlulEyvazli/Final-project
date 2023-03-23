package com.example.bazaarstore.dto;

import com.example.bazaarstore.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdressDTO {

    String country;

    String city;

    String street;

    String postalCode;

}
