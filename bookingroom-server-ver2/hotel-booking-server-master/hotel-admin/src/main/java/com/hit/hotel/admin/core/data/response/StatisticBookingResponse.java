package com.hit.hotel.admin.core.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticBookingResponse {

    private Integer totalBookingCheckin;

    private Integer totalBookingCheckout;

    private Integer totalBookingPending;

    private Integer totalBookingCancel;

    public Integer getTotalBookingCheckin() {
        return totalBookingCheckin == null ? 0 : totalBookingCheckin;
    }

    public Integer getTotalBookingCheckout() {
        return totalBookingCheckout == null ? 0 : totalBookingCheckout;
    }

    public Integer getTotalBookingPending() {
        return totalBookingPending == null ? 0 : totalBookingPending;
    }

    public Integer getTotalBookingCancel() {
        return totalBookingCancel == null ? 0 : totalBookingCancel;
    }
}
