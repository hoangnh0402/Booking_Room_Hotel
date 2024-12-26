package com.hit.hotel.admin.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.response.UserDetailResponse;
import com.hit.hotel.admin.core.data.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/user")
public interface UserOperations {

    @Operation(summary = "API get all user")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<UserResponse>>> getUsers(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @RequestParam(defaultValue = "true") Boolean isEnable,
            @RequestParam(defaultValue = "false") Boolean isLocked);

    @Operation(summary = "API user detail by id")
    @GetMapping("/{userId}")
    ResponseEntity<GeneralResponse<UserDetailResponse>> getUserDetail(@PathVariable String userId);


    @Operation(summary = "API lock or unlock user")
    @PostMapping("/lock-unlock/{userId}")
    ResponseEntity<GeneralResponse<CommonResponse>> lockAndUnlockUser(@PathVariable String userId,
                                                                      @RequestParam(defaultValue = "true") Boolean isLocked);

    @Operation(summary = "API delete user locked")
    @DeleteMapping("/locked/delete/{userId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteUserLocked(@PathVariable String userId);

}
