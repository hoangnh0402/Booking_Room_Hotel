package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.service.CreateServiceRequest;
import com.hit.hotel.admin.core.data.request.service.UpdateServiceRequest;
import com.hit.hotel.admin.core.data.response.ServiceDetailResponse;
import com.hit.hotel.admin.core.data.response.ServiceResponse;

public interface HotelService {

    PaginationResponse<ServiceResponse> getServices(PaginationRequest paginationRequest);

    ServiceDetailResponse getServiceDetail(Integer id);

    ServiceResponse createService(UserPrincipal userPrincipal, CreateServiceRequest request);

    ServiceResponse updateService(UserPrincipal userPrincipal, UpdateServiceRequest request);

    CommonResponse deleteService(Integer serviceId);

    CommonResponse deleteTrashService(Integer serviceId);

    CommonResponse restoreTrashService(Integer serviceId);

}
