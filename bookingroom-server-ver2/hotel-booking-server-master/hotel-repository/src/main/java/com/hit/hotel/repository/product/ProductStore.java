package com.hit.hotel.repository.product;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.product.model.ProductDetailProjection;

public interface ProductStore {

    Product get(Integer id);

    Product save(Product product);

    Product update(Product product);

    boolean exists(Integer id);

    PaginationResponse<Product> getProductsByServiceId(PaginationRequest request, Integer serviceId);

    PaginationResponse<ProductDetailProjection> getProductDetails(PaginationRequest request);

    ProductDetailProjection getProductDetail(Integer id);

    boolean existsTrash(Integer productId);

    void deleteToTrashById(Integer productId);

    void deleteTrashById(Integer productId);

    void restoreTrashById(Integer productId);

}
