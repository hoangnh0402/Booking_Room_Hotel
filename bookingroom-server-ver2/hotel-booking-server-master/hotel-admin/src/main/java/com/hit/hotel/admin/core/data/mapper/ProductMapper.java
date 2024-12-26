package com.hit.hotel.admin.core.data.mapper;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.hotel.admin.core.data.request.product.CreateProductRequest;
import com.hit.hotel.admin.core.data.request.product.UpdateProductRequest;
import com.hit.hotel.admin.core.data.response.ProductDetailResponse;
import com.hit.hotel.admin.core.data.response.ProductResponse;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.product.model.ProductDetailProjection;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>AdminImpl")
public interface ProductMapper extends AuditingMapper {

    ProductResponse toProductResponse(ProductDetailProjection product);

    @Mapping(target = "creator", expression = "java(this.toCreator(userPrincipal))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(userPrincipal))")
    ProductResponse toProductResponse(Product product, @Context UserPrincipal userPrincipal);

    @Mapping(target = "creator", expression = "java(this.toCreator(creator))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(modifier))")
    ProductResponse toProductResponse(Product product, @Context User creator, @Context UserPrincipal modifier);

    ProductDetailResponse toProductDetailResponse(ProductDetailProjection product);

    Product toProduct(CreateProductRequest request);

    void updateProduct(UpdateProductRequest request, @MappingTarget Product product);

}
