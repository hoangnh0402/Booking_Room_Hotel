package com.hit.hotel.repository.notification.model;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@Setter
@Getter
@NoArgsConstructor
public class NotificationPaginationResponse<T> extends PaginationResponse<T> {

    private Integer totalUnreadNotifications;

    public NotificationPaginationResponse(PaginationRequest request, Page<T> page, Integer totalUnreadNotifications) {
        super(request, page);
        this.totalUnreadNotifications = totalUnreadNotifications;
    }

    @Override
    public <N> NotificationPaginationResponse<N> map(Function<? super T, ? extends N> mapper) {
        NotificationPaginationResponse<N> newPage = new NotificationPaginationResponse<>();
        newPage.setItems(CollectionUtils.isEmpty(this.getItems()) ? null : this.getItems().stream().<N>map(mapper).toList());
        newPage.setMeta(this.getMeta());
        newPage.setTotalUnreadNotifications(this.totalUnreadNotifications);
        return newPage;
    }
}
