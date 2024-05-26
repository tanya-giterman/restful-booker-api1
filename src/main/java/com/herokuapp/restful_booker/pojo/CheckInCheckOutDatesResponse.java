package com.herokuapp.restful_booker.pojo;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckInCheckOutDatesResponse {

    private String checkin;
    private String checkout;
}
