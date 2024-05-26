package com.herokuapp.restful_booker.pojo;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SingleBookingByIdResponse {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private CheckInCheckOutDatesResponse bookingdates;
    private String additionalneeds;


}

