package com.hit.hotel.core.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.ServiceResponse;
import com.hit.hotel.core.core.service.HotelService;
import com.hit.hotel.core.ui.rest.ServiceOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServiceController implements ServiceOperations {

    private final HotelService hotelService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<ServiceResponse>>>
    getServices(PaginationRequest paginationRequest) {
        return ResponseFactory.success(hotelService.getServicesPreBook(paginationRequest));
    }
}
