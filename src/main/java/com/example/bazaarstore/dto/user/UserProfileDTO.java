package com.example.bazaarstore.dto.user;

import com.example.bazaarstore.dto.product.ProductDTO;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private String username;
    private String email;
    private String phoneNumber;
    private List<ProductDTO> list;
}
