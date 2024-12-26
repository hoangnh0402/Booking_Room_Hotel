package com.hit.hotel.admin.core.data.request.service;

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
public class UpdateServiceRequest {

    private Integer serviceId;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String title;

    private String thumbnail;

    @ValidFile
    private MultipartFile newThumbnailFile;

    private Integer price;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String description;

    private boolean isPreBook;

}
