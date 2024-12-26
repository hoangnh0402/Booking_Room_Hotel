package com.hit.hotel.core.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.RoomDetailResponse;
import com.hit.hotel.core.core.data.response.RoomResponse;
import com.hit.hotel.common.domain.request.RoomFilterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("${app.core-api-path-prefix}/api/v1/room")
public interface RoomOperations {

    @Operation(summary = "API get all room")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<RoomResponse>>> getRooms(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @Valid @ParameterObject RoomFilterRequest filterRequest);

    @Operation(summary = "API get room detail")
    @GetMapping("/{roomId}")
    ResponseEntity<GeneralResponse<RoomDetailResponse>> getRoomDetail(@PathVariable Integer roomId);

}
