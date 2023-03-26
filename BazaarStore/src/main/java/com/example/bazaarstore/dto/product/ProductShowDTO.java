package com.example.bazaarstore.dto.product;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductShowDTO {

    private Long productId;

    private String categoryName;

    private String sku;

    private String name;

    private String description;

    private double unitPrice;

    private byte[] image;

    private int unitsInStock;

    private String username;

}
