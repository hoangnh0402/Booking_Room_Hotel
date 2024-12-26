package com.hit.hotel.admin.core.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSummaryResponse {

    private Integer id;

    private String title;

    private String thumbnail;

    private Integer price;

}
