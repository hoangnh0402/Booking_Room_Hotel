package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.service.CreateServiceRequest;
import com.hit.hotel.admin.core.data.request.service.UpdateServiceRequest;
import com.hit.hotel.admin.core.data.response.ServiceDetailResponse;
import com.hit.hotel.admin.core.data.response.ServiceResponse;
import com.hit.hotel.admin.core.service.HotelService;
import com.hit.hotel.admin.ui.rest.ServiceOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("serviceControllerAdmin")
public class ServiceController implements ServiceOperations {

    private final HotelService hotelService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<ServiceResponse>>>
    getServices(PaginationRequest paginationRequest) {
        return ResponseFactory.success(hotelService.getServices(paginationRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<ServiceDetailResponse>> getServiceDetail(Integer serviceId) {
        return ResponseFactory.success(hotelService.getServiceDetail(serviceId));
    }

    @Override
    public ResponseEntity<GeneralResponse<ServiceResponse>> createService(UserPrincipal userPrincipal,
                                                                          CreateServiceRequest request) {
        return ResponseFactory.success(hotelService.createService(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<ServiceResponse>> updateService(UserPrincipal userPrincipal,
                                                                          Integer serviceId,
                                                                          UpdateServiceRequest request) {
        request.setServiceId(serviceId);
        return ResponseFactory.success(hotelService.updateService(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteService(Integer serviceId) {
        return ResponseFactory.success(hotelService.deleteService(serviceId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashService(Integer serviceId) {
        return ResponseFactory.success(hotelService.deleteTrashService(serviceId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashService(Integer serviceId) {
        return ResponseFactory.success(hotelService.restoreTrashService(serviceId));
    }
}
