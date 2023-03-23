package com.example.bazaarstore.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long productId;

    private String categoryName;

    private String sku;

    private String name;

    private String description;

    private double unitPrice;

    private String imageUrl;

    private int unitsInStock;

    private String username;

}
