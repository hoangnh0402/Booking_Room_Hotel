package com.hit.hotel.admin.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.room.AddSaleRequest;
import com.hit.hotel.admin.core.data.request.room.CreateRoomRequest;
import com.hit.hotel.admin.core.data.request.room.RoomFilterRequest;
import com.hit.hotel.admin.core.data.request.room.UpdateRoomRequest;
import com.hit.hotel.admin.core.data.response.RoomAvailableResponse;
import com.hit.hotel.admin.core.data.response.RoomDetailResponse;
import com.hit.hotel.admin.core.data.response.RoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/room")
public interface RoomOperations {

    @Operation(summary = "API get all room")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<RoomResponse>>> getRooms(
            @Valid @ParameterObject PaginationRequest paginationRequest);

    @Operation(summary = "API get all room status")
    @GetMapping("/available")
    ResponseEntity<GeneralResponse<PaginationResponse<RoomAvailableResponse>>> getRoomsAvailable(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @Valid @ParameterObject RoomFilterRequest filterRequest);

    @Operation(summary = "API get room detail by id")
    @GetMapping("/{roomId}")
    ResponseEntity<GeneralResponse<RoomDetailResponse>> getRoomDetail(@PathVariable Integer roomId);


    @Operation(summary = "API create room")
    @PostMapping
    ResponseEntity<GeneralResponse<RoomResponse>> createRoom(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @Valid @RequestBody CreateRoomRequest request);

    @Operation(summary = "API update room")
    @PutMapping(value = "/{roomId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<GeneralResponse<RoomResponse>> updateRoom(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @PathVariable Integer roomId,
            @Valid @ModelAttribute UpdateRoomRequest request);

    @Operation(summary = "API delete room")
    @DeleteMapping("/{roomId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteRoom(@PathVariable Integer roomId);

    @Operation(summary = "API delete room trash")
    @DeleteMapping("/trash/delete/{roomId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashRoom(@PathVariable Integer roomId);

    @Operation(summary = "API restore room trash")
    @PostMapping("/trash/restore/{roomId}")
    ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashRoom(@PathVariable Integer roomId);

    @Operation(summary = "API add sale to rooms")
    @PostMapping("/add-sale")
    ResponseEntity<GeneralResponse<CommonResponse>> addSaleToRooms(@RequestBody AddSaleRequest request);

    @Operation(summary = "API delete sale from rooms")
    @PostMapping("/delete-sale")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteSaleFromRooms(@RequestBody List<Integer> roomIds);

}
