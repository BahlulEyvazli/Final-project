package com.example.bazaarstore.dto.user;

import com.example.bazaarstore.dto.product.ProductShowDTO;
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
    private byte[] profile;
    private List<ProductShowDTO> list;
}
