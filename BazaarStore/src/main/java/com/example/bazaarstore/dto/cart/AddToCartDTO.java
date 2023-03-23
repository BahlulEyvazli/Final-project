package com.example.bazaarstore.dto.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartDTO {

     Long id;
     Long productId;
     Integer quantity;
}
