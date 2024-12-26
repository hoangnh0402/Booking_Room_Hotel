package com.hit.hotel.core.core.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse {

    private Integer id;

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
