package com.hit.common.core.domain.paginationv2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Order {

    @Schema(title = "Tên trường cần sort")
    private String property;

    @Schema(title = "asc: tăng dần, desc: giảm dần")
    private String direction;

    public Order(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }

    public enum Direction {
        ASC, DESC;
    }
}
