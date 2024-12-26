package com.hit.hotel.admin.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.service.CreateServiceRequest;
import com.hit.hotel.admin.core.data.request.service.UpdateServiceRequest;
import com.hit.hotel.admin.core.data.response.ServiceDetailResponse;
import com.hit.hotel.admin.core.data.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/service")
public interface ServiceOperations {

    @Operation(summary = "API get all service")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<ServiceResponse>>>
    getServices(@Valid @ParameterObject PaginationRequest paginationRequest);

    @Operation(summary = "API service detail by id")
    @GetMapping("/{serviceId}")
    ResponseEntity<GeneralResponse<ServiceDetailResponse>> getServiceDetail(@PathVariable Integer serviceId);

    @Operation(summary = "API create service")
    @PostMapping
    ResponseEntity<GeneralResponse<ServiceResponse>> createService(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @Valid @RequestBody CreateServiceRequest request);

    @Operation(summary = "API update service")
    @PutMapping(value = "/{serviceId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<GeneralResponse<ServiceResponse>> updateService(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @PathVariable Integer serviceId,
            @Valid @ModelAttribute UpdateServiceRequest request);

    @Operation(summary = "API delete service")
    @DeleteMapping("/{serviceId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteService(@PathVariable Integer serviceId);

    @Operation(summary = "API delete service trash")
    @DeleteMapping("/trash/delete/{serviceId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashService(@PathVariable Integer serviceId);

    @Operation(summary = "API restore service trash")
    @PostMapping("/trash/restore/{serviceId}")
    ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashService(@PathVariable Integer serviceId);
}
