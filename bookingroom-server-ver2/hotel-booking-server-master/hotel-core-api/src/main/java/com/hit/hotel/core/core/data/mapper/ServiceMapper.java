package com.hit.hotel.core.core.data.mapper;

import com.hit.hotel.core.core.data.response.ServiceResponse;
import com.hit.hotel.repository.service.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper extends AuditingMapper {

    ServiceResponse toServiceResponse(Service service);

}
