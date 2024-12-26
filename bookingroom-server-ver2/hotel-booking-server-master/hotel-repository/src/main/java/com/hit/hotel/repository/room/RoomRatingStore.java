package com.hit.hotel.repository.room;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.room.entity.RoomRating;
import com.hit.hotel.repository.room.model.projection.RoomRatingProjection;

public interface RoomRatingStore {

    RoomRating get(Integer roomRatingId);

    RoomRating save(RoomRating roomRating);

    RoomRating update(RoomRating roomRating);

    PaginationResponse<RoomRatingProjection> getRoomRatingByRoomIdAndStar(PaginationRequest paginationRequest,
                                                                          Integer roomId, Integer star);

}
