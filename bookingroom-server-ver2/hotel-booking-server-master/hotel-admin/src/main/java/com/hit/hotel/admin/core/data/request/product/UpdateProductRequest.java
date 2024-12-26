package com.hit.hotel.admin.core.data.request.product;

import com.hit.common.annotation.ValidFile;
import com.hit.common.core.constants.ErrorMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private Integer productId;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String name;

    private String thumbnail;

    @ValidFile
    private MultipartFile newThumbnailFile;

    private String description;

    private Integer serviceId;

}
