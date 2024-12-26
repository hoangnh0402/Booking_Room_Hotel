package com.hit.common.core.domain.paginationv2;

import com.hit.common.core.domain.query.Filter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Pageable {

    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAXIMUM_PAGE_SIZE = 500;

    @Schema(title = "page tìm kiếm: bắt đấu từ 1")
    private int page;

    @Schema(title = "từ khóa tìm kiếm")
    private String keyword;

    @Schema(title = "số lượng item của 1 page")
    private int pageSize;

    @Schema(title = "Trường hợp muốn lấy từ offset")
    private Integer offset;

    @Schema(title = "Thời gian bắt đầu với trường hợp tìm kiếm/thống kê theo khoảng thời gian")
    private LocalDateTime fromDate;

    @Schema(title = "Thời gian kết thúc với trường hợp tìm kiếm/thống kê theo khoảng thời gian")
    private LocalDateTime toDate;

    private Long total;

    private List<Order> sorts;

    private List<Filter> filters;

    private Boolean loadMoreAble;

    public Pageable() {
        this.page = DEFAULT_PAGE;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.offset = Math.max((page - 1) * pageSize, 0);
        this.total = 0L;
    }

    public Pageable(int page, int pageSize) {
        this.page = page > 0 ? page : DEFAULT_PAGE;
        this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
        this.offset = Math.max((page - 1) * pageSize, 0);
        this.total = 0L;
    }

    public Integer getOffset() {
        if (offset == null || offset <= 0) {
            return Math.max((page - 1) * pageSize, 0);
        }
        return offset;
    }

    public String getKeyword() {
        if (keyword == null) return null;
        return keyword.trim();
    }

    public void addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
    }
}
