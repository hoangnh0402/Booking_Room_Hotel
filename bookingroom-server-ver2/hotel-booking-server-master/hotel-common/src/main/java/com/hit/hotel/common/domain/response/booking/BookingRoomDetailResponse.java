package com.hit.hotel.common.domain.response.booking;

import com.hit.hotel.common.domain.response.MediaResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRoomDetailResponse {

    private Integer bookingPrice;

    private Float salePercent;

    private RoomInfoResponse room;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoResponse {

        private String name;

        private Integer price;

        private String type;

        private String bed;

        private Integer size;

        private Integer capacity;

        private String services;

        private String description;

        private List<MediaResponse> medias;
    }

}
