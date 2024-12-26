package com.hit.hotel.repository.service.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.service.ServiceStore;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.service.model.projection.ServiceDetailProjection;
import com.hit.hotel.repository.service.repository.ServiceRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ServiceAdapter extends BaseJPAAdapter<Service, Integer, ServiceRepository> implements ServiceStore {

    @Override
    protected Class<Service> getEntityClass() {
        return Service.class;
    }

    @Override
    public PaginationResponse<Service> getServicesPreBook(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<Service> services = this.repository.getServices(request.getKeyword(), Boolean.TRUE, request.getDeleteFlag(), pageable);
        return new PaginationResponse<>(request, services);
    }

    @Override
    public PaginationResponse<ServiceDetailProjection> getServicesDetails(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<ServiceDetailProjection> services = this.repository.getServiceDetail(
                request.getKeyword(),
                request.getDeleteFlag(),
                pageable
        );
        return new PaginationResponse<>(request, services);
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
