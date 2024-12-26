package com.hit.hotel.common.domain.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSurchargeResponse {

    private long surcharge;

    private String reason;

}
