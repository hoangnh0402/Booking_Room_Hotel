package com.hit.hotel.core.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("${app.core-api-path-prefix}/api/v1/product")
public interface ProductOperations {

    @Operation(summary = "API get all product by service id")
    @GetMapping("/service/{serviceId}")
    ResponseEntity<GeneralResponse<PaginationResponse<ProductResponse>>> getProductsByService(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @PathVariable Integer serviceId);

}
