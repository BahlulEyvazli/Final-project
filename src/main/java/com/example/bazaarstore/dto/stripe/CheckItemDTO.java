package com.example.bazaarstore.dto.stripe;


import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckItemDTO{

     private Long productId;

     private int userId;

     private String productName;

     private Long  quantity;

     private double price;
}
