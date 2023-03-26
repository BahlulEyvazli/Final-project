package com.example.bazaarstore.dto.cart;

import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private int quantity;

    private Product product;

    private double cost;
}
