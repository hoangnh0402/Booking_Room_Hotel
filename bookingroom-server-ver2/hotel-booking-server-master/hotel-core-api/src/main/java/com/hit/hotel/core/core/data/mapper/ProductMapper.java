package com.hit.hotel.core.core.data.mapper;

import com.hit.hotel.core.core.data.response.ProductResponse;
import com.hit.hotel.repository.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends AuditingMapper {

    ProductResponse toProductResponse(Product product);

}
