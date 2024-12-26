package com.hit.hotel.core.core.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelBookingRequest {

    private Integer bookingId;

    @NotEmpty(message = "Bạn cần cung cấp lý do hủy phòng")
    private String note;

}
