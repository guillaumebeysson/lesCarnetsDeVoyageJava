package com.carnetdevoyage;

import lombok.Data;

@Data
public class UserDto {
    String username;
    Long id;
    String password;
    String grantType;
    String refreshToken;
}
