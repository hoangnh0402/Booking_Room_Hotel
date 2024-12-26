package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.product.CreateProductRequest;
import com.hit.hotel.admin.core.data.request.product.UpdateProductRequest;
import com.hit.hotel.admin.core.data.response.ProductDetailResponse;
import com.hit.hotel.admin.core.data.response.ProductResponse;
import com.hit.hotel.admin.core.service.ProductService;
import com.hit.hotel.admin.ui.rest.ProductOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("productControllerAdmin")
public class ProductController implements ProductOperations {

    private final ProductService productService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<ProductResponse>>> getProducts(PaginationRequest paginationRequest) {
        return ResponseFactory.success(productService.getProducts(paginationRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<ProductDetailResponse>> getProductDetail(Integer productId) {
        return ResponseFactory.success(productService.getProductDetail(productId));
    }

    @Override
    public ResponseEntity<GeneralResponse<ProductResponse>> createProduct(UserPrincipal userPrincipal,
                                                                          CreateProductRequest request) {
        return ResponseFactory.success(productService.createProduct(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<ProductResponse>> updateProduct(UserPrincipal userPrincipal,
                                                                          Integer productId,
                                                                          UpdateProductRequest request) {
        request.setProductId(productId);
        return ResponseFactory.success(productService.updateProduct(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteProduct(Integer productId) {
        return ResponseFactory.success(productService.deleteProduct(productId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashProduct(Integer productId) {
        return ResponseFactory.success(productService.deleteTrashProduct(productId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashProduct(Integer productId) {
        return ResponseFactory.success(productService.restoreTrashProduct(productId));
    }
}
