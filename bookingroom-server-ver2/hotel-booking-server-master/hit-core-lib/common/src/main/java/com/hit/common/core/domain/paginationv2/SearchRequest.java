package com.hit.common.core.domain.paginationv2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hit.common.core.domain.query.Filter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class SearchRequest {

    private long total;

    @Schema(title = "page tìm kiếm: bắt đấu từ 1")
    private int page;

    @Schema(title = "từ khóa tìm kiếm")
    private String keyword;

    @Schema(title = "số lượng item của 1 page")
    private int pageSize = 10;

    @Schema(title = "Thời gian bắt đầu với trường hợp tìm kiếm/thống kê theo khoảng thời gian")
    private LocalDateTime fromDate;

    @Schema(title = "Thời gian kết thúc với trường hợp tìm kiếm/thống kê theo khoảng thời gian")
    private LocalDateTime toDate;

    private List<Order> sorts;

    private List<Filter> filters;

    @JsonIgnore
    public Integer getOffset() {
        return Math.max((page - 1) * pageSize, 0);
    }

    public int getPageSize() {
        if (this.pageSize < 0) return Pageable.MAXIMUM_PAGE_SIZE;
        return pageSize;
    }

    public List<Order> getSorts() {
        if (sorts == null) {
            sorts = new ArrayList<>();
        }
        if (sorts.isEmpty()) {
            sorts.add(new Order()
                    .setProperty("id")
                    .setDirection(Order.Direction.DESC.name()));
        }
        return sorts;
    }

    public String getKeyword() {
        if (keyword == null) return null;
        return keyword.trim();
    }

    public List<Filter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        return filters;
    }

    public SearchRequest addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
        return this;
    }
}