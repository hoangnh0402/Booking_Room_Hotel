package com.hit.hotel.repository.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceDTO {

    private Integer id;

    private String title;

    private String thumbnail;

    private Integer price;

}
