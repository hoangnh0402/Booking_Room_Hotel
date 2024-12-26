package com.hit.hotel.admin.core.data.request.room;

import com.hit.common.annotation.ValidFiles;
import com.hit.common.core.constants.ErrorMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {

    @NotEmpty(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private String name;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer price;

    @NotEmpty(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private String type;

    @NotEmpty(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private String bed;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer size;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer capacity;

    @NotEmpty(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private String services;

    @NotEmpty(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private String description;

    @ValidFiles
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private List<MultipartFile> files;

}
