package com.hit.hotel.admin.core.data.mapper;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.hotel.admin.core.data.request.sale.CreateSaleRequest;
import com.hit.hotel.admin.core.data.request.sale.UpdateSaleRequest;
import com.hit.hotel.admin.core.data.response.SaleDetailResponse;
import com.hit.hotel.admin.core.data.response.SaleResponse;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.sale.model.projection.SaleDetailProjection;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>AdminImpl")
public interface SaleMapper extends AuditingMapper {

    SaleResponse toSaleResponse(SaleDetailProjection sale);

    SaleDetailResponse toSaleDetailResponse(SaleDetailProjection sale);

    @Mapping(target = "creator", expression = "java(this.toCreator(userPrincipal))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(userPrincipal))")
    SaleResponse toSaleResponse(Sale sale, @Context UserPrincipal userPrincipal);

    @Mapping(target = "creator", expression = "java(this.toCreator(creator))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(modifier))")
    SaleResponse toSaleResponse(Sale sale, @Context User creator, @Context UserPrincipal modifier);

    Sale toSale(CreateSaleRequest request);

    void updateSale(UpdateSaleRequest request, @MappingTarget Sale sale);

}
