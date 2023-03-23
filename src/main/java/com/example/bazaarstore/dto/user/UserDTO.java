package com.example.bazaarstore.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String phoneNumber;
}
