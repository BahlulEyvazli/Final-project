package com.example.bazaarstore.dto.cart;

import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.model.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDTO {

     Long id;
     Integer quantity;
     Product product;

     public CartItemDTO(Cart cart) {
          this.setId(cart.getId());
          this.setQuantity(cart.getQuantity());
          this.setProduct(cart.getProduct());
     }
}
