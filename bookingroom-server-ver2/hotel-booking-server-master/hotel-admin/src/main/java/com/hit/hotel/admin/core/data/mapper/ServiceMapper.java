package com.hit.hotel.admin.core.data.mapper;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.hotel.admin.core.data.request.service.CreateServiceRequest;
import com.hit.hotel.admin.core.data.request.service.UpdateServiceRequest;
import com.hit.hotel.admin.core.data.response.ServiceDetailResponse;
import com.hit.hotel.admin.core.data.response.ServiceResponse;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.service.model.projection.ServiceDetailProjection;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>AdminImpl")
public interface ServiceMapper extends AuditingMapper {

    ServiceResponse toServiceResponse(ServiceDetailProjection serviceDetailProjection);

    @Mapping(target = "creator", expression = "java(this.toCreator(userPrincipal))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(userPrincipal))")
    ServiceResponse toServiceResponse(Service service, @Context UserPrincipal userPrincipal);

    @Mapping(target = "creator", expression = "java(this.toCreator(creator))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(modifier))")
    ServiceResponse toServiceResponse(Service service, @Context User creator, @Context UserPrincipal modifier);

    ServiceDetailResponse toServiceDetailResponse(Service service);

    Service toService(CreateServiceRequest request);

    void updateService(UpdateServiceRequest request, @MappingTarget Service service);

}
