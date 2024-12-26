package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.utils.UploadFileUtils;
import com.hit.hotel.admin.core.data.mapper.ProductMapper;
import com.hit.hotel.admin.core.data.request.product.CreateProductRequest;
import com.hit.hotel.admin.core.data.request.product.UpdateProductRequest;
import com.hit.hotel.admin.core.data.response.ProductDetailResponse;
import com.hit.hotel.admin.core.data.response.ProductResponse;
import com.hit.hotel.admin.core.service.ProductService;
import com.hit.hotel.repository.product.ProductStore;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.service.ServiceStore;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@RequiredArgsConstructor
@org.springframework.stereotype.Service("productServiceAdmin")
public class ProductServiceImpl implements ProductService {

    private final ProductStore productStore;

    private final ServiceStore serviceStore;

    private final UserStore userStore;

    private final ProductMapper productMapper;

    private final UploadFileUtils uploadFileUtils;

    @Override
    public PaginationResponse<ProductResponse> getProducts(PaginationRequest paginationRequest) {
        return productStore.getProductDetails(paginationRequest).map(productMapper::toProductResponse);
    }

    @Override
    public ProductDetailResponse getProductDetail(Integer id) {
        return productMapper.toProductDetailResponse(productStore.getProductDetail(id));
    }

    @Override
    public ProductResponse createProduct(UserPrincipal userPrincipal, CreateProductRequest request) {
        Service service = serviceStore.get(request.getServiceId());
        if (ObjectUtils.isEmpty(service)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        Product product = productMapper.toProduct(request);
        product.setService(service);
        product.setThumbnail(uploadFileUtils.uploadFile(request.getThumbnailFile()));
        productStore.save(product);
        return productMapper.toProductResponse(product, userPrincipal);
    }

    @Override
    public ProductResponse updateProduct(UserPrincipal userPrincipal, UpdateProductRequest request) {
        Product product = productStore.get(request.getProductId());
        if (ObjectUtils.isEmpty(product)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.PRODUCT_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(request.getThumbnail())) {
            if (ObjectUtils.isEmpty(request.getNewThumbnailFile())) {
                throw new BaseResponseException(ResponseStatusCodeEnum.VALIDATION_ERROR);
            }
            uploadFileUtils.destroyFileWithUrl(product.getThumbnail());
            request.setThumbnail(uploadFileUtils.uploadFile(request.getNewThumbnailFile()));
        }
        productMapper.updateProduct(request, product);
        productStore.update(product);
        User creator = userStore.get(product.getCreatedBy());
        return productMapper.toProductResponse(product, creator, userPrincipal);
    }

    @Override
    public CommonResponse deleteProduct(Integer productId) {
        if (Boolean.FALSE.equals(productStore.exists(productId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.PRODUCT_NOT_FOUND);
        }
        productStore.deleteToTrashById(productId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse deleteTrashProduct(Integer productId) {
        if (Boolean.FALSE.equals(productStore.existsTrash(productId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.PRODUCT_NOT_FOUND);
        }
        productStore.deleteTrashById(productId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse restoreTrashProduct(Integer productId) {
        if (Boolean.FALSE.equals(productStore.existsTrash(productId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.PRODUCT_NOT_FOUND);
        }
        productStore.restoreTrashById(productId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.RESTORE_SUCCESS);
    }

}
