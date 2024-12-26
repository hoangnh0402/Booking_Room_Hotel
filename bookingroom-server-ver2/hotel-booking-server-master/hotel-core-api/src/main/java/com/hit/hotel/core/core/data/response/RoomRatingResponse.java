package com.hit.hotel.core.core.data.response;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.DateAuditingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRatingResponse extends DateAuditingDTO {

    private Integer id;

    private Integer star;

    private String comment;

    private CreatorDTO creator;

}
