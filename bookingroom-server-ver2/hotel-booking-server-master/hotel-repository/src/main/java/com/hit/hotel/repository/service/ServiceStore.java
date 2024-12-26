package com.hit.hotel.repository.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.service.model.projection.ServiceDetailProjection;

import java.util.Collection;
import java.util.Map;

public interface ServiceStore {

    Service get(Integer id);

    Map<Integer, Service> getMapId(Collection<Integer> ids);

    Service save(Service service);

    Service update(Service service);

    boolean exists(Integer id);

    PaginationResponse<Service> getServicesPreBook(PaginationRequest request);

    PaginationResponse<ServiceDetailProjection> getServicesDetails(PaginationRequest request);

    boolean existsTrash(Integer serviceId);

    void deleteToTrashById(Integer serviceId);

    void deleteTrashById(Integer serviceId);

    void restoreTrashById(Integer serviceId);

}
