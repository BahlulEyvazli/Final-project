package com.example.bazaarstore.dto.stripe;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CheckItemDTO {

     String productName;
     Long  quantity;
     double price;
     long productId;
     int userId;
}
