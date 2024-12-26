package com.hit.hotel.admin.core.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSummaryResponse {

    private Integer id;

    private String name;

    private Integer price;

    private String type;

}
