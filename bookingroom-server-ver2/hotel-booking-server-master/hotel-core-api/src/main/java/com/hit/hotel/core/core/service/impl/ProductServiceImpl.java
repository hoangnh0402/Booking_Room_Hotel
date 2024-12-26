package com.hit.hotel.core.core.service.impl;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.mapper.ProductMapper;
import com.hit.hotel.core.core.data.response.ProductResponse;
import com.hit.hotel.core.core.service.ProductService;
import com.hit.hotel.repository.product.ProductStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ProductServiceImpl implements ProductService {

    private final ProductStore productStore;

    private final ProductMapper productMapper;

    @Override
    public PaginationResponse<ProductResponse> getProductByServiceId(PaginationRequest paginationRequest,
                                                                     Integer serviceId) {
        return productStore.getProductsByServiceId(paginationRequest, serviceId).map(productMapper::toProductResponse);
    }
}
