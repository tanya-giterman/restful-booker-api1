package com.herokuapp.restful_booker.pojo;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTokenRequest {
    private String username;
    private String password;
}
