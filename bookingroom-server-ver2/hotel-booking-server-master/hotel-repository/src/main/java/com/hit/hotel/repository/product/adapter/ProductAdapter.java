package com.hit.hotel.repository.product.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.product.ProductStore;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.product.model.ProductDetailProjection;
import com.hit.hotel.repository.product.repository.ProductRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductAdapter extends BaseJPAAdapter<Product, Integer, ProductRepository> implements ProductStore {

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    public PaginationResponse<Product> getProductsByServiceId(PaginationRequest request, Integer serviceId) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<Product> products = this.repository.getProductsByServiceId(serviceId, request, pageable);
        return new PaginationResponse<>(request, products);
    }

    @Override
    public PaginationResponse<ProductDetailProjection> getProductDetails(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<ProductDetailProjection> products = this.repository.getProductDetails(request, pageable);
        return new PaginationResponse<>(request, products);
    }

    @Override
    public ProductDetailProjection getProductDetail(Integer id) {
        return this.repository.getProductDetail(id);
    }

    @Override
    public boolean existsTrash(Integer serviceId) {
        return this.repository.existsTrashById(serviceId);
    }

    @Override
    public void deleteToTrashById(Integer serviceId) {
        this.repository.updateDeleteFlagById(serviceId, Boolean.TRUE);
    }

    @Override
    public void deleteTrashById(Integer serviceId) {
        this.delete(serviceId);
    }

    @Override
    public void restoreTrashById(Integer serviceId) {
        this.repository.updateDeleteFlagById(serviceId, Boolean.FALSE);
    }

}
