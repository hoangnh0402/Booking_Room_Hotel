package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.sale.CreateSaleRequest;
import com.hit.hotel.admin.core.data.request.sale.UpdateSaleRequest;
import com.hit.hotel.admin.core.data.response.SaleDetailResponse;
import com.hit.hotel.admin.core.data.response.SaleResponse;
import com.hit.hotel.admin.core.service.SaleService;
import com.hit.hotel.admin.ui.rest.SaleOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("saleControllerAdmin")
public class SaleController implements SaleOperations {

    private final SaleService saleService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<SaleResponse>>> getSales(PaginationRequest paginationRequest) {
        return ResponseFactory.success(saleService.getSales(paginationRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<SaleDetailResponse>> getSale(Integer saleId) {
        return ResponseFactory.success(saleService.getSaleDetail(saleId));
    }

    @Override
    public ResponseEntity<GeneralResponse<SaleResponse>> createSale(UserPrincipal userPrincipal,
                                                                    CreateSaleRequest request) {
        return ResponseFactory.success(saleService.createSale(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<SaleResponse>> updateSale(UserPrincipal userPrincipal,
                                                                    Integer saleId,
                                                                    UpdateSaleRequest request) {
        request.setSaleId(saleId);
        return ResponseFactory.success(saleService.updateSale(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteSale(Integer saleId) {
        return ResponseFactory.success(saleService.deleteSale(saleId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashSale(Integer saleId) {
        return ResponseFactory.success(saleService.deleteTrashSale(saleId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashSale(Integer saleId) {
        return ResponseFactory.success(saleService.restoreTrashSale(saleId));
    }
}
