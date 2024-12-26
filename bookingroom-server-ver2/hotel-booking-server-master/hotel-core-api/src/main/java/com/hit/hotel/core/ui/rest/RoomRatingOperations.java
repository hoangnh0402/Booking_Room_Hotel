package com.hit.hotel.core.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.core.core.data.request.RoomRatingCreateRequest;
import com.hit.hotel.core.core.data.request.RoomRatingUpdateRequest;
import com.hit.hotel.core.core.data.response.RoomRatingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.core-api-path-prefix}/api/v1/room-rating")
public interface RoomRatingOperations {

    @Operation(summary = "API get all room rating by room id")
    @GetMapping("/room/{roomId}")
    ResponseEntity<GeneralResponse<PaginationResponse<RoomRatingResponse>>> getRoomRatingsByRoom(
            @Valid @ParameterObject PaginationRequest request,
            @PathVariable Integer roomId,
            @RequestParam(required = false) Integer star);

    @Operation(summary = "API create room rating")
    @PostMapping("/{roomId}")
    ResponseEntity<GeneralResponse<RoomRatingResponse>> createRoomRating(
            @PathVariable Integer roomId,
            @Valid @RequestBody RoomRatingCreateRequest request,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

    @Operation(summary = "API update room rating")
    @PutMapping("/{roomRatingId}")
    ResponseEntity<GeneralResponse<RoomRatingResponse>> updateRoomRating(
            @PathVariable Integer roomRatingId,
            @Valid @RequestBody RoomRatingUpdateRequest request,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

    @Operation(summary = "API delete room rating")
    @DeleteMapping("/{roomRatingId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteRoomRating(
            @PathVariable Integer roomRatingId,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

}
