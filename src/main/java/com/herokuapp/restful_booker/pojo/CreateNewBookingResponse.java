package com.herokuapp.restful_booker.pojo;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateNewBookingResponse {

        private int bookingid;
        private SingleBookingByIdResponse booking;
}

