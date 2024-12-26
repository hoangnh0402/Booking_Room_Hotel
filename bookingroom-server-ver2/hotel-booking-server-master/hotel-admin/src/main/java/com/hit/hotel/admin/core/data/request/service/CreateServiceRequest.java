package com.hit.hotel.admin.core.data.request.service;

import com.hit.common.annotation.ValidFile;
import com.hit.common.core.constants.ErrorMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceRequest {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String title;

    @ValidFile
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private MultipartFile thumbnailFile;

    private Integer price;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String description;

    private boolean isPreBook;

}
