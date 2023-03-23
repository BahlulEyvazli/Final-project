package com.example.bazaarstore.dto.comment;

import com.example.bazaarstore.dto.UserDTO;
import com.example.bazaarstore.dto.product.ProductDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private String content;

    private String username;

    private String productName;
}
