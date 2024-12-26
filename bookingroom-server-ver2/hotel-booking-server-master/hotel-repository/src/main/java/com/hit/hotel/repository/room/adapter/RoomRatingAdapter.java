package com.hit.hotel.repository.room.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.room.RoomRatingStore;
import com.hit.hotel.repository.room.entity.RoomRating;
import com.hit.hotel.repository.room.model.projection.RoomRatingProjection;
import com.hit.hotel.repository.room.repository.RoomRatingRepository;
import com.hit.jpa.BaseJPAAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RoomRatingAdapter extends BaseJPAAdapter<RoomRating, Integer, RoomRatingRepository> implements RoomRatingStore {

    @Override
    protected Class<RoomRating> getEntityClass() {
        return RoomRating.class;
    }

    @Override
    public PaginationResponse<RoomRatingProjection> getRoomRatingByRoomIdAndStar(PaginationRequest paginationRequest,
                                                                                 Integer roomId, Integer star) {
        Pageable pageable = PaginationUtils.buildPageableNative(paginationRequest);
        Page<RoomRatingProjection> roomRatings = this.repository.getRoomRatingByRoomIdAndStar(roomId, star, pageable);
        return new PaginationResponse<>(paginationRequest, roomRatings);
    }

}
