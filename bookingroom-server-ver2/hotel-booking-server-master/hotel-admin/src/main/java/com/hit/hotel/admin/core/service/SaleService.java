package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.sale.CreateSaleRequest;
import com.hit.hotel.admin.core.data.request.sale.UpdateSaleRequest;
import com.hit.hotel.admin.core.data.response.SaleDetailResponse;
import com.hit.hotel.admin.core.data.response.SaleResponse;

public interface SaleService {

    PaginationResponse<SaleResponse> getSales(PaginationRequest paginationRequest);

    SaleDetailResponse getSaleDetail(Integer saleId);

    SaleResponse createSale(UserPrincipal userPrincipal, CreateSaleRequest request);

    SaleResponse updateSale(UserPrincipal userPrincipal, UpdateSaleRequest request);

    CommonResponse deleteSale(Integer saleId);

    CommonResponse deleteTrashSale(Integer saleId);

    CommonResponse restoreTrashSale(Integer saleId);
}
