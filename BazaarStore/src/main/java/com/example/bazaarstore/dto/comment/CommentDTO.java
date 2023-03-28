package com.example.bazaarstore.dto.comment;

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
