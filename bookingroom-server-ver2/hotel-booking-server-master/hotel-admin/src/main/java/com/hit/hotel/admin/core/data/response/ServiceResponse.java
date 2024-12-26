package com.hit.hotel.admin.core.data.response;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.DateAuditingDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse extends DateAuditingDTO {

    private Integer id;

    private String title;

    private String thumbnail;

    private Integer price;

    private String description;

    private CreatorDTO creator;

    private ModifierDTO modifier;

}
