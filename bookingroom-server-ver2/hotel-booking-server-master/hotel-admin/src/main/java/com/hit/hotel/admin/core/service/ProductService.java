package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.product.CreateProductRequest;
import com.hit.hotel.admin.core.data.request.product.UpdateProductRequest;
import com.hit.hotel.admin.core.data.response.ProductDetailResponse;
import com.hit.hotel.admin.core.data.response.ProductResponse;

public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(PaginationRequest paginationRequest);

    ProductDetailResponse getProductDetail(Integer id);

    ProductResponse createProduct(UserPrincipal userPrincipal, CreateProductRequest request);

    ProductResponse updateProduct(UserPrincipal userPrincipal, UpdateProductRequest request);

    CommonResponse deleteProduct(Integer productId);

    CommonResponse deleteTrashProduct(Integer productId);

    CommonResponse restoreTrashProduct(Integer productId);
}
