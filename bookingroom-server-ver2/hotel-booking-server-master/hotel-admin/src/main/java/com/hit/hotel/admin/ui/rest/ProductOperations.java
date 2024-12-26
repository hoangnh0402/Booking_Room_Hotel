package com.hit.hotel.admin.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.product.CreateProductRequest;
import com.hit.hotel.admin.core.data.request.product.UpdateProductRequest;
import com.hit.hotel.admin.core.data.response.ProductDetailResponse;
import com.hit.hotel.admin.core.data.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/product")
public interface ProductOperations {

    @Operation(summary = "API get all product")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<ProductResponse>>> getProducts(
            @Valid @ParameterObject PaginationRequest paginationRequest);

    @Operation(summary = "API product detail by id")
    @GetMapping("/{productId}")
    ResponseEntity<GeneralResponse<ProductDetailResponse>> getProductDetail(@PathVariable Integer productId);

    @Operation(summary = "API create product")
    @PostMapping
    ResponseEntity<GeneralResponse<ProductResponse>> createProduct(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @Valid @RequestBody CreateProductRequest request);

    @Operation(summary = "API update product")
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<GeneralResponse<ProductResponse>> updateProduct(
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal,
            @PathVariable Integer productId,
            @Valid @ModelAttribute UpdateProductRequest request);

    @Operation(summary = "API delete product")
    @DeleteMapping("/{productId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteProduct(@PathVariable Integer productId);

    @Operation(summary = "API delete product trash")
    @DeleteMapping("/trash/delete/{productId}")
    ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashProduct(@PathVariable Integer productId);

    @Operation(summary = "API restore product trash")
    @PostMapping("/trash/restore/{productId}")
    ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashProduct(@PathVariable Integer productId);

}
