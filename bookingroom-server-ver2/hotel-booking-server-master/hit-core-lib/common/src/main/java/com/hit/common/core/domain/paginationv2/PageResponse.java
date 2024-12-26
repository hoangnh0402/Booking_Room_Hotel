package com.hit.common.core.domain.paginationv2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class PageResponse<T> {
    private String key;
    private Long total;
    private Integer page;
    private Collection<T> items;
    private String maxId;
    private Boolean loadMoreAble;
    private Boolean preLoadAble;

    public PageResponse() {
    }

    public PageResponse(Long total, Integer page, Collection<T> items) {
        this.page = page;
        this.total = total;
        this.items = items;
    }

    public PageResponse(Long total, Pageable pageable, Collection<T> items) {
        this.total = total;
        this.page = pageable.getPage();
        this.items = items;
        this.loadMoreAble = total != null &&
                            (total.intValue() > (pageable.getOffset() + pageable.getPageSize()));
    }

    public PageResponse(Pageable pageable, Collection<T> items) {
        this.items = items;
        this.total = pageable.getTotal();
        this.page = pageable.getPage();
        this.loadMoreAble = pageable.getLoadMoreAble();
    }

    public PageResponse(Long total, SearchRequest searchRequest, List<T> items) {
        this.total = total;
        this.page = searchRequest.getPage();
        this.items = items;
        this.loadMoreAble = total != null &&
                            (total.intValue() > (searchRequest.getOffset() + searchRequest.getPageSize()));
    }

    public PageResponse(SearchRequest searchRequest, List<T> items) {
        this.total = searchRequest.getTotal();
        this.page = searchRequest.getPage();
        this.items = items;
        this.loadMoreAble = total != null &&
                            (total.intValue() > (searchRequest.getOffset() + searchRequest.getPageSize()));
    }
}
