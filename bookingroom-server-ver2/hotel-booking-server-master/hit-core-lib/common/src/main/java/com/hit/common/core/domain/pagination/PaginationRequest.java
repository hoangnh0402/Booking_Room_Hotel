package com.hit.common.core.domain.pagination;

import com.hit.common.core.constants.CommonConstants;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    @Parameter(description = "Page you want to retrieve (0..N)")
    private Integer pageNum = CommonConstants.ZERO_INT_VALUE;

    @Parameter(description = "Number of records per page.")
    private Integer pageSize = CommonConstants.ZERO_INT_VALUE;

    @Parameter(description = "The name of property want to sort.")
    private String sortBy = CommonConstants.EMPTY_STRING;

    @Parameter(description = "Sorting criteria: ASC|DESC. Default sort order is descending.")
    private String sortType = Direction.DESC.name();

    @Parameter(description = "Keyword to search.")
    private String keyword = CommonConstants.EMPTY_STRING;

    @Parameter(description = "Item active is false. Item trash is true")
    private Boolean deleteFlag = Boolean.FALSE;

    public int getPageNum() {
        if (pageNum < 1) {
            pageNum = CommonConstants.ONE_INT_VALUE;
        }
        return pageNum - 1;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = CommonConstants.PAGE_SIZE_DEFAULT;
        }
        return pageSize;
    }

    public String getSortType() {
        return sortType.trim().toUpperCase();
    }

    public boolean isAscending() {
        return Direction.ASC.name().equals(this.getSortType());
    }

    public boolean isDescending() {
        return Direction.DESC.name().equals(this.getSortType());
    }

    public String getKeyword() {
        return keyword.trim();
    }

    public enum Direction {
        ASC, DESC;
    }
}
