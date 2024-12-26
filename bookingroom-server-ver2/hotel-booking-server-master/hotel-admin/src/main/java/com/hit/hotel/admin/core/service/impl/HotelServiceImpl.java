package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.utils.UploadFileUtils;
import com.hit.hotel.admin.core.data.mapper.ServiceMapper;
import com.hit.hotel.admin.core.data.request.service.CreateServiceRequest;
import com.hit.hotel.admin.core.data.request.service.UpdateServiceRequest;
import com.hit.hotel.admin.core.data.response.ServiceDetailResponse;
import com.hit.hotel.admin.core.data.response.ServiceResponse;
import com.hit.hotel.admin.core.service.HotelService;
import com.hit.hotel.repository.service.ServiceStore;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@RequiredArgsConstructor
@org.springframework.stereotype.Service("hotelServiceAdmin")
public class HotelServiceImpl implements HotelService {

    private final ServiceStore serviceStore;

    private final UserStore userStore;

    private final ServiceMapper serviceMapper;

    private final UploadFileUtils uploadFileUtils;

    @Override
    public PaginationResponse<ServiceResponse> getServices(PaginationRequest paginationRequest) {
        return serviceStore.getServicesDetails(paginationRequest).map(serviceMapper::toServiceResponse);
    }

    @Override
    public ServiceDetailResponse getServiceDetail(Integer id) {
        return serviceMapper.toServiceDetailResponse(serviceStore.get(id));
    }

    @Override
    public ServiceResponse createService(UserPrincipal userPrincipal, CreateServiceRequest request) {
        Service service = serviceMapper.toService(request);
        service.setThumbnail(uploadFileUtils.uploadFile(request.getThumbnailFile()));
        serviceStore.save(service);
        return serviceMapper.toServiceResponse(service, userPrincipal);
    }

    @Override
    public ServiceResponse updateService(UserPrincipal userPrincipal, UpdateServiceRequest request) {
        Service service = serviceStore.get(request.getServiceId());
        if (ObjectUtils.isEmpty(service)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        if (Boolean.TRUE.equals(request.isPreBook()) && ObjectUtils.isEmpty(request.getPrice())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.VALIDATION_ERROR);
        }
        if (ObjectUtils.isEmpty(request.getThumbnail())) {
            if (ObjectUtils.isEmpty(request.getNewThumbnailFile())) {
                throw new BaseResponseException(ResponseStatusCodeEnum.VALIDATION_ERROR);
            }
            uploadFileUtils.destroyFileWithUrl(service.getThumbnail());
            request.setThumbnail(uploadFileUtils.uploadFile(request.getNewThumbnailFile()));
        }

        serviceMapper.updateService(request, service);
        if (ObjectUtils.isEmpty(request.getThumbnail())) {
            if (request.getNewThumbnailFile() != null) {
                uploadFileUtils.destroyFileWithUrl(service.getThumbnail());
                service.setThumbnail(uploadFileUtils.uploadFile(request.getNewThumbnailFile()));
            } else {
                throw new BaseResponseException(ResponseStatusCodeEnum.VALIDATION_ERROR);
            }
        }
        serviceStore.update(service);
        User creator = userStore.get(service.getCreatedBy());
        return serviceMapper.toServiceResponse(service, creator, userPrincipal);
    }

    @Override
    public CommonResponse deleteService(Integer serviceId) {
        if (Boolean.FALSE.equals(serviceStore.exists(serviceId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        serviceStore.deleteToTrashById(serviceId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse deleteTrashService(Integer serviceId) {
        if (Boolean.FALSE.equals(serviceStore.existsTrash(serviceId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        serviceStore.deleteTrashById(serviceId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse restoreTrashService(Integer serviceId) {
        if (Boolean.FALSE.equals(serviceStore.existsTrash(serviceId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        serviceStore.restoreTrashById(serviceId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.RESTORE_SUCCESS);
    }
}
