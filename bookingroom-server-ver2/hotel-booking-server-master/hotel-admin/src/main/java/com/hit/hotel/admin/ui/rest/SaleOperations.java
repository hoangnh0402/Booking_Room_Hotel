package com.hit.hotel.admin.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.sale.CreateSaleRequest;
import com.hit.hotel.admin.core.data.request.sale.UpdateSaleRequest;
import com.hit.hotel.admin.core.data.response.SaleDetailResponse;
import com.hit.hotel.admin.core.data.response.SaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/sale")
public interface SaleOperations {

    @Operation(summary = "API get all sale")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<SaleResponse>>>
    getSales(@Valid @ParameterObject PaginationRequest paginationRequest);

    @Operation(summary = "API sale by id")
    @GetMapping("/{saleId}")
    ResponseEntity<GeneralResponse<SaleDetailResponse>> getSale(@PathVariable Integer saleId);

    @Operation(summary = "API create sale")
    @PostMapping
    ResponseEntity<GeneralResponse<SaleResponse>> createSale(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @Valid @RequestBody CreateSaleRequest request);

    @Operation(summary = "API update sale")
    @PutMapping(value = "/{saleId}")
    ResponseEntity<GeneralResponse<SaleResponse>> updateSale(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @PathVariable Integer saleId,
            @Valid @RequestBody UpdateSaleRequest request);

    @Operation(summary = "API delete sale")
    @DeleteMapping("/{saleId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteSale(@PathVariable Integer saleId);

    @Operation(summary = "API delete sale trash")
    @DeleteMapping("/trash/delete/{saleId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashSale(@PathVariable Integer saleId);

    @Operation(summary = "API restore sale trash")
    @PostMapping("/trash/restore/{saleId}")
    ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashSale(@PathVariable Integer saleId);

}
