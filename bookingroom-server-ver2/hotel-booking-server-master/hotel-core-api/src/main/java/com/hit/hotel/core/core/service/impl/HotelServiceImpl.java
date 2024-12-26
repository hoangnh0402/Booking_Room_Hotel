package com.hit.hotel.core.core.service.impl;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.mapper.ServiceMapper;
import com.hit.hotel.core.core.data.response.ServiceResponse;
import com.hit.hotel.core.core.service.HotelService;
import com.hit.hotel.repository.service.ServiceStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class HotelServiceImpl implements HotelService {

    private final ServiceStore serviceStore;

    private final ServiceMapper serviceMapper;

    @Override
    public PaginationResponse<ServiceResponse> getServicesPreBook(PaginationRequest paginationRequest) {
        return serviceStore.getServicesPreBook(paginationRequest).map(serviceMapper::toServiceResponse);
    }
}
