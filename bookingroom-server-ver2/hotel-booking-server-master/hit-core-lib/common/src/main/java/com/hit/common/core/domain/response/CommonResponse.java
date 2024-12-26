package com.hit.common.core.domain.response;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommonResponse {

    private Boolean status;

    private String message;

}
