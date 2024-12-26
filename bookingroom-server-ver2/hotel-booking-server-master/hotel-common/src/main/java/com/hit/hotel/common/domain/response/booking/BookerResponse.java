package com.hit.hotel.common.domain.response.booking;

import com.hit.common.core.domain.dto.CreatorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookerResponse extends CreatorDTO {

    private String email;

    private String phoneNumber;

}
