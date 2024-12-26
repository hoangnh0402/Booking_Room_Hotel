package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.admin.core.data.mapper.SaleMapper;
import com.hit.hotel.admin.core.data.request.sale.CreateSaleRequest;
import com.hit.hotel.admin.core.data.request.sale.UpdateSaleRequest;
import com.hit.hotel.admin.core.data.response.SaleDetailResponse;
import com.hit.hotel.admin.core.data.response.SaleResponse;
import com.hit.hotel.admin.core.service.SaleService;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.sale.SaleStore;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.sale.model.projection.SaleDetailProjection;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service("saleServiceAdmin")
public class SaleServiceImpl implements SaleService {

    private final SaleStore saleStore;

    private final RoomStore roomStore;

    private final UserStore userStore;

    private final SaleMapper saleMapper;

    @Override
    public PaginationResponse<SaleResponse> getSales(PaginationRequest paginationRequest) {
        return saleStore.getSaleDetails(paginationRequest).map(saleMapper::toSaleResponse);
    }

    @Override
    public SaleDetailResponse getSaleDetail(Integer saleId) {
        SaleDetailProjection sale = saleStore.getSaleDetail(saleId);
        if (ObjectUtils.isEmpty(sale)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        return saleMapper.toSaleDetailResponse(sale);
    }

    @Override
    public SaleResponse createSale(UserPrincipal userPrincipal, CreateSaleRequest request) {
        Sale sale = saleMapper.toSale(request);
        saleStore.save(sale);
        return saleMapper.toSaleResponse(sale, userPrincipal);
    }

    @Override
    public SaleResponse updateSale(UserPrincipal userPrincipal, UpdateSaleRequest request) {
        Sale sale = saleStore.get(request.getSaleId());
        if (ObjectUtils.isEmpty(sale)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        saleMapper.updateSale(request, sale);
        saleStore.update(sale);
        User creator = userStore.get(sale.getCreatedBy());
        return saleMapper.toSaleResponse(sale, creator, userPrincipal);
    }

    @Override
    @Transactional
    public CommonResponse deleteSale(Integer saleId) {
        if (Boolean.FALSE.equals(saleStore.exists(saleId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        saleStore.deleteToTrashById(saleId);
        roomStore.deleteSaleFromRooms(saleId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse deleteTrashSale(Integer saleId) {
        if (Boolean.FALSE.equals(saleStore.existsTrash(saleId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        saleStore.deleteTrashById(saleId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse restoreTrashSale(Integer saleId) {
        if (Boolean.FALSE.equals(saleStore.existsTrash(saleId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        saleStore.restoreTrashById(saleId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.RESTORE_SUCCESS);
    }

}
