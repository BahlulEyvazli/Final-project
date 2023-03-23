package com.example.bazaarstore.dto.wishlist;


import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.dto.user.UserDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListDTO {

    private List<ProductDTO> productDTOList;
}
