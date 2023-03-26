package com.example.bazaarstore.dto.cart;

import com.example.bazaarstore.dto.product.ProductShowDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private List<CartItemDTO> cartItemDTOS;

    private double totalCost;

}
