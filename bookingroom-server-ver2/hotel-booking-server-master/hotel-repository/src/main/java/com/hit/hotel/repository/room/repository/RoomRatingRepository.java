package com.hit.hotel.repository.room.repository;

import com.hit.hotel.repository.room.entity.RoomRating;
import com.hit.hotel.repository.room.model.projection.RoomRatingProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface RoomRatingRepository extends BaseJPARepository<RoomRating, Integer> {

    @Query(value = """
                SELECT r.id,
                       r.star,
                       r.comment,
                       r.room_id                 AS roomId,
                       r.created_date            AS createdDate,
                       r.last_modified_date      AS lastModifiedDate,
                       creator.id                AS creatorId,
                       creator.first_name        AS creatorFirstName,
                       creator.last_name         AS creatorLastName,
                       creator.avatar            AS creatorAvatar
                FROM room_rating r
                         LEFT JOIN user creator ON r.created_by = creator.id
                WHERE r.room_id = :roomId
                  AND (:star IS NULL OR r.star = :star)
                  AND r.delete_flag = 0
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM room_rating r
                                 LEFT JOIN user creator ON r.created_by = creator.id
                        WHERE r.room_id = :roomId
                          AND (:star IS NULL OR r.star = :star)
                          AND r.delete_flag = 0
                    """,
            nativeQuery = true)
    Page<RoomRatingProjection> getRoomRatingByRoomIdAndStar(Integer roomId, Integer star, Pageable pageable);


}
