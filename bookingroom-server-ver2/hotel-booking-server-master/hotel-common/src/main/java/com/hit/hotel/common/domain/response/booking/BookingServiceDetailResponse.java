package com.hit.hotel.common.domain.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingServiceDetailResponse {

    private ServiceInfoResponse service;

    private Integer quantity;

    private Integer bookingPrice;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceInfoResponse {

        private String title;

        private String thumbnail;

        private Integer price;

    }

}
