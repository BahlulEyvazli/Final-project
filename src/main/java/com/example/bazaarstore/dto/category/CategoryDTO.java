package com.example.bazaarstore.dto.category;

import com.example.bazaarstore.dto.product.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private String categoryName;

    private List<ProductDTO> productDTOS;
}
