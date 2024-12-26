package com.hit.common.utils;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PagingMeta;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PaginationUtils {

    public static Pageable buildPageable(PaginationRequest request) {
        return PageRequest.of(request.getPageNum(), request.getPageSize());
    }

    public static Pageable buildPageableJPQL(PaginationRequest request) {
        if (StringUtils.isEmptyOrBlank(request.getSortBy())) {
            return buildPageable(request);
        }
        Sort.Direction direction = BooleanUtils.isTrue(request.isAscending()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, request.getSortBy());
        return PageRequest.of(request.getPageNum(), request.getPageSize(), sort);
    }

    public static Pageable buildPageableNative(PaginationRequest request) {
        if (StringUtils.isEmptyOrBlank(request.getSortBy())) {
            return buildPageable(request);
        }
        Sort.Direction direction = BooleanUtils.isTrue(request.isAscending()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, StringUtils.camelToSnake(request.getSortBy()));
        return PageRequest.of(request.getPageNum(), request.getPageSize(), sort);
    }

    public static <T> PagingMeta buildPagingMeta(PaginationRequest request, Page<T> pages) {
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                request.getPageNum() + CommonConstants.ONE_INT_VALUE,
                request.getPageSize(),
                request.getSortBy(),
                BooleanUtils.isTrue(request.isAscending()) ? CommonConstants.SORT_TYPE_ASC : CommonConstants.SORT_TYPE_DESC
        );
    }
}
