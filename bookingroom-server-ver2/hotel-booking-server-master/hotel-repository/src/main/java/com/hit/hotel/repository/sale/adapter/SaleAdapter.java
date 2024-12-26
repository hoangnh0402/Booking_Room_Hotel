package com.hit.hotel.repository.sale.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.sale.SaleStore;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.sale.model.projection.SaleDetailProjection;
import com.hit.hotel.repository.sale.repository.SaleRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SaleAdapter extends BaseJPAAdapter<Sale, Integer, SaleRepository> implements SaleStore {

    @Override
    protected Class<Sale> getEntityClass() {
        return Sale.class;
    }

    @Override
    public PaginationResponse<SaleDetailProjection> getSaleDetails(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<SaleDetailProjection> sales = this.repository.getSaleDetails(request, pageable);
        return new PaginationResponse<>(request, sales);
    }

    @Override
    public SaleDetailProjection getSaleDetail(Integer saleId) {
        return this.repository.getSaleDetail(saleId);
    }

    @Override
    public boolean existsTrash(Integer serviceId) {
        return this.repository.existsTrashById(serviceId);
    }

    @Override
    public void deleteToTrashById(Integer serviceId) {
        this.repository.updateDeleteFlagById(serviceId, Boolean.TRUE);
    }

    @Override
    public void deleteTrashById(Integer serviceId) {
        this.delete(serviceId);
    }

    @Override
    public void restoreTrashById(Integer serviceId) {
        this.repository.updateDeleteFlagById(serviceId, Boolean.FALSE);
    }
}
