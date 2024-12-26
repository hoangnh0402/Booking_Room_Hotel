package com.hit.hotel.core.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.ProductResponse;
import com.hit.hotel.core.core.service.ProductService;
import com.hit.hotel.core.ui.rest.ProductOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductOperations {

    private final ProductService productService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<ProductResponse>>>
    getProductsByService(PaginationRequest paginationRequest, Integer serviceId) {
        return ResponseFactory.success(productService.getProductByServiceId(paginationRequest, serviceId));
    }
}
