package com.hit.hotel.admin.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/room-rating")
public interface RoomRatingOperations {

    @Operation(summary = "API delete room rating")
    @DeleteMapping("/{roomRatingId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteRoomRating(
            @PathVariable Integer roomRatingId,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

}
