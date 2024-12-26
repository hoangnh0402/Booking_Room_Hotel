package com.hit.hotel.common.domain.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailResponse extends BookingResponse {

    private Set<BookingRoomDetailResponse> rooms = new HashSet<>();

    private Set<BookingServiceDetailResponse> services = new HashSet<>();

    private List<BookingSurchargeResponse> surcharges = new ArrayList<>();

}
