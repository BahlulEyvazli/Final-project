package com.example.bazaarstore.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Check implements Serializable {

    private String storeName;

    private String storeMail;

    private String productName;

    private String productSku;

    private String message;
}
