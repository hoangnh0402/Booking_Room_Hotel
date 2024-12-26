package com.hit.hotel.core.core.data.response;

import com.hit.common.core.domain.dto.DateAuditingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends DateAuditingDTO {

    private Long id;

    private String name;

    private String thumbnail;

    private String description;

}
