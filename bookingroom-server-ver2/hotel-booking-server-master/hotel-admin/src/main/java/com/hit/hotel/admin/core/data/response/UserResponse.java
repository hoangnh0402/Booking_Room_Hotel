package com.hit.hotel.admin.core.data.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hit.common.core.domain.dto.DateAuditingDTO;
import com.hit.common.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends DateAuditingDTO {

    private String id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_PATTERN)
    private LocalDate birthday;

    private String address;

    private String avatar;

    private Boolean isEnable;

    private Boolean isLocked;

}

