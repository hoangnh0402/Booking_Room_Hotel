package com.hit.hotel.repository.sale;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.sale.model.projection.SaleDetailProjection;

public interface SaleStore {

    Sale get(Integer id);

    Sale save(Sale sale);

    Sale update(Sale sale);

    boolean exists(Integer id);

    PaginationResponse<SaleDetailProjection> getSaleDetails(PaginationRequest request);

    SaleDetailProjection getSaleDetail(Integer saleId);

    boolean existsTrash(Integer productId);

    void deleteToTrashById(Integer productId);

    void deleteTrashById(Integer productId);

    void restoreTrashById(Integer productId);

}
