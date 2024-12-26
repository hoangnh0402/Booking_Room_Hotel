package com.hit.hotel.core.core.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.ProductResponse;

public interface ProductService {

    PaginationResponse<ProductResponse> getProductByServiceId(PaginationRequest paginationRequest, Integer serviceId);

}
