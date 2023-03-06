package com.example.bazaarstore.dto;

import com.example.bazaarstore.model.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private Long categoryId;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private String imageUrl;

    private boolean active;

    private int unitsInStock;

    private Date dateCreated;

    private Date lastUpdated;
    private User user;
}
