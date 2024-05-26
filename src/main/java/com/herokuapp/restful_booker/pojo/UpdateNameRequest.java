package com.herokuapp.restful_booker.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateNameRequest {
    private String firstname;
    private String lastname;
}
