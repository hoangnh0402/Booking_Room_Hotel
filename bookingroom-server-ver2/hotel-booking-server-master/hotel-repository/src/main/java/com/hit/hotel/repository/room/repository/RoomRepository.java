package com.hit.hotel.repository.room.repository;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends BaseJPARepository<Room, Integer> {

    @Query(value = """
                SELECT r.id,
                       r.name,
                       r.price,
                       r.type,
                       r.bed,
                       r.size,
                       r.capacity,
                       r.services,
                       r.description,
                       r.created_date                                                               AS createdDate,
                       r.last_modified_date                                                         AS lastModifiedDate,
                       s.id                                                                         AS saleId,
                       s.day_start                                                                  AS saleDayStart,
                       s.day_end                                                                    AS saleDayEnd,
                       s.sale_percent                                                               AS saleSalePercent,
                       creator.id                                                                   AS creatorId,
                       creator.first_name                                                           AS creatorFirstName,
                       creator.last_name                                                            AS creatorLastName,
                       creator.avatar                                                               AS creatorAvatar,
                       modifier.id                                                                  AS modifierId,
                       modifier.first_name                                                          AS modifierFirstName,
                       modifier.last_name                                                           AS modifierLastName,
                       modifier.avatar                                                              AS modifierAvatar
                FROM room r
                    LEFT JOIN sale s ON s.id = r.sale_id
                    LEFT JOIN user creator ON r.created_by = creator.id
                    LEFT JOIN user modifier ON r.last_modified_by = modifier.id
                WHERE (
                        COALESCE(:#{#request.keyword}, '') = ''
                        OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.capacity LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND (:#{#request.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM room r
                        LEFT JOIN sale s ON s.id = r.sale_id
                        LEFT JOIN user creator ON r.created_by = creator.id
                        LEFT JOIN user modifier ON r.last_modified_by = modifier.id
                    WHERE (
                        COALESCE(:#{#request.keyword}, '') = ''
                        OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.capacity LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND (:#{#request.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
                    """,
            nativeQuery = true)
    Page<RoomDetailPjt> getRoomDetailPjts(PaginationRequest request, Pageable pageable);


    @Query(value = """
                WITH data_check_checkin AS (
                    SELECT r.*
                    FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= :#{#condition.checkIn}
                        AND b.expected_check_out >= DATE_SUB(:#{#condition.checkIn}, INTERVAL 2 HOUR)
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                ),
                data_check_checkout AS (
                    SELECT r.*
                    FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= DATE_ADD(:#{#condition.checkOut}, INTERVAL 2 HOUR)
                        AND b.expected_check_out >= :#{#condition.checkOut}
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                ),
                data_unavailable AS (
                    SELECT * FROM data_check_checkin data_1
                    UNION
                    SELECT * FROM data_check_checkout data_2
                )
                SELECT r.id,
                       r.name,
                       r.price,
                       r.type,
                       r.bed,
                       r.size,
                       r.capacity,
                       r.services,
                       r.description,
                       r.created_date                                                               AS createdDate,
                       r.last_modified_date                                                         AS lastModifiedDate,
                       CASE WHEN r.id IN (SELECT id FROM data_unavailable) THEN FALSE ELSE TRUE END AS isAvailable,
                       s.id                                                                         AS saleId,
                       s.day_start                                                                  AS saleDayStart,
                       s.day_end                                                                    AS saleDayEnd,
                       s.sale_percent                                                               AS saleSalePercent
                FROM room r
                    LEFT JOIN sale s ON s.id = r.sale_id
                WHERE (:#{#condition.capacity} IS NULL OR r.capacity = :#{#condition.capacity})
                    AND (:#{#condition.roomType} IS NULL OR r.type LIKE CONCAT('%', :#{#condition.roomType}, '%'))
                    AND (
                    COALESCE(:#{#request.keyword}, '') = ''
                        OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND (:#{#request.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
            """,
            countQuery = """
                        WITH data_check_checkin AS (
                            SELECT r.*
                            FROM room r
                                INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                                INNER JOIN booking b ON b.id = brd.booking_id
                            WHERE b.expected_check_in <= :#{#condition.checkIn}
                                AND b.expected_check_out >= DATE_SUB(:#{#condition.checkIn}, INTERVAL 2 HOUR)
                                AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                        ),
                        data_check_checkout AS (
                            SELECT r.*
                            FROM room r
                                INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                                INNER JOIN booking b ON b.id = brd.booking_id
                            WHERE b.expected_check_in <= DATE_ADD(:#{#condition.checkOut}, INTERVAL 2 HOUR)
                                AND b.expected_check_out >= :#{#condition.checkOut}
                                AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                        ),
                        data_unavailable AS (
                            SELECT * FROM data_check_checkin data_1
                            UNION
                            SELECT * FROM data_check_checkout data_2
                        )
                        SELECT COUNT(*)
                        FROM room r
                            LEFT JOIN sale s ON s.id = r.sale_id
                        WHERE (:#{#condition.capacity} IS NULL OR r.capacity = :#{#condition.capacity})
                            AND (:#{#condition.roomType} IS NULL OR r.type LIKE CONCAT('%', :#{#condition.roomType}, '%'))
                            AND (
                            COALESCE(:#{#request.keyword}, '') = ''
                                OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                            AND (:#{#condition.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
                    """,
            nativeQuery = true)
    Page<RoomAvailablePjt> getRoomAvailablePjts(PaginationRequest request, RoomFilterCondition condition,
                                                Pageable pageable);

    @Query(value = """
                WITH data_check_checkin AS (
                    SELECT r.*
                    FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= :#{#condition.checkIn}
                        AND b.expected_check_out >= DATE_SUB(:#{#condition.checkIn}, INTERVAL 2 HOUR)
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                ),
                data_check_checkout AS (
                    SELECT r.*
                    FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= DATE_ADD(:#{#condition.checkOut}, INTERVAL 2 HOUR)
                        AND b.expected_check_out >= :#{#condition.checkOut}
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                ),
                data_unavailable AS (
                    SELECT * FROM data_check_checkin data_1
                    UNION
                    SELECT * FROM data_check_checkout data_2
                )
                SELECT r.id,
                       r.name,
                       r.price,
                       r.type,
                       r.bed,
                       r.size,
                       r.capacity,
                       r.services,
                       r.description,
                       r.created_date                                                               AS createdDate,
                       r.last_modified_date                                                         AS lastModifiedDate,
                       CASE WHEN r.id IN (SELECT id FROM data_unavailable) THEN FALSE ELSE TRUE END AS isAvailable,
                       s.id                                                                         AS saleId,
                       s.day_start                                                                  AS saleDayStart,
                       s.day_end                                                                    AS saleDayEnd,
                       s.sale_percent                                                               AS saleSalePercent,
                       creator.id                                                                   AS creatorId,
                       creator.first_name                                                           AS creatorFirstName,
                       creator.last_name                                                            AS creatorLastName,
                       creator.avatar                                                               AS creatorAvatar,
                       modifier.id                                                                  AS modifierId,
                       modifier.first_name                                                          AS modifierFirstName,
                       modifier.last_name                                                           AS modifierLastName,
                       modifier.avatar                                                              AS modifierAvatar
                FROM room r
                    LEFT JOIN sale s ON s.id = r.sale_id
                    LEFT JOIN user creator ON r.created_by = creator.id
                    LEFT JOIN user modifier ON r.last_modified_by = modifier.id
                WHERE (:#{#condition.capacity} IS NULL OR r.capacity = :#{#condition.capacity})
                    AND (:#{#condition.roomType} IS NULL OR r.type LIKE CONCAT('%', :#{#condition.roomType}, '%'))
                    AND (
                    COALESCE(:#{#request.keyword}, '') = ''
                        OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND (:#{#request.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
            """,
            countQuery = """
                        WITH data_check_checkin AS (
                            SELECT r.*
                            FROM room r
                                INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                                INNER JOIN booking b ON b.id = brd.booking_id
                            WHERE b.expected_check_in <= :#{#condition.checkIn}
                                AND b.expected_check_out >= DATE_SUB(:#{#condition.checkIn}, INTERVAL 2 HOUR)
                                AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                        ),
                        data_check_checkout AS (
                            SELECT r.*
                            FROM room r
                                INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                                INNER JOIN booking b ON b.id = brd.booking_id
                            WHERE b.expected_check_in <= DATE_ADD(:#{#condition.checkOut}, INTERVAL 2 HOUR)
                                AND b.expected_check_out >= :#{#condition.checkOut}
                                AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                        ),
                        data_unavailable AS (
                            SELECT * FROM data_check_checkin data_1
                            UNION
                            SELECT * FROM data_check_checkout data_2
                        )
                        SELECT COUNT(*)
                        FROM room r
                            LEFT JOIN user creator ON r.created_by = creator.id
                            LEFT JOIN user modifier ON r.last_modified_by = modifier.id
                            LEFT JOIN sale s ON s.id = r.sale_id
                        WHERE (:#{#condition.capacity} IS NULL OR r.capacity = :#{#condition.capacity})
                            AND (:#{#condition.roomType} IS NULL OR r.type LIKE CONCAT('%', :#{#condition.roomType}, '%'))
                            AND (
                            COALESCE(:#{#request.keyword}, '') = ''
                                OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR r.type LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR r.price LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                            AND (:#{#request.deleteFlag} IS NULL OR r.delete_flag = :#{#request.deleteFlag})
                    """,
            nativeQuery = true)
    Page<RoomDetailAvailablePjt> getRoomDetailAvailablePjts(PaginationRequest request, RoomFilterCondition condition,
                                                            Pageable pageable);

    @Query(value = """
                SELECT r.id,
                       r.name,
                       r.price,
                       r.type,
                       r.bed,
                       r.size,
                       r.capacity,
                       r.services,
                       r.description,
                       r.created_date                                                               AS createdDate,
                       r.last_modified_date                                                         AS lastModifiedDate,
                       s.id                                                                         AS saleId,
                       s.day_start                                                                  AS saleDayStart,
                       s.day_end                                                                    AS saleDayEnd,
                       s.sale_percent                                                               AS saleSalePercent,
                       creator.id                                                                   AS creatorId,
                       creator.first_name                                                           AS creatorFirstName,
                       creator.last_name                                                            AS creatorLastName,
                       creator.avatar                                                               AS creatorAvatar,
                       modifier.id                                                                  AS modifierId,
                       modifier.first_name                                                          AS modifierFirstName,
                       modifier.last_name                                                           AS modifierLastName,
                       modifier.avatar                                                              AS modifierAvatar
                FROM room r
                    LEFT JOIN sale s ON s.id = r.sale_id
                    LEFT JOIN user creator ON r.created_by = creator.id
                    LEFT JOIN user modifier ON r.last_modified_by = modifier.id
                WHERE r.id = :roomId
                    AND r.delete_flag = false
            """,
            nativeQuery = true)
    RoomDetailPjt getRoomDetailPjtActive(Integer roomId);

    @Query(value = """
                WITH data_check_checkin AS (
                    SELECT r.*
                    FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= :checkin
                        AND b.expected_check_out >= DATE_SUB(:checkin, INTERVAL 2 HOUR)
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                ),
                data_check_checkout AS (
                    SELECT r.* FROM room r
                        INNER JOIN booking_room_detail brd ON brd.room_id = r.id
                        INNER JOIN booking b ON b.id = brd.booking_id
                    WHERE b.expected_check_in <= DATE_ADD(:checkout, INTERVAL 2 HOUR)
                        AND b.expected_check_out >= :checkout
                        AND b.status NOT IN ('CANCEL', 'CHECKED_OUT')
                )
                SELECT * FROM data_check_checkin
                UNION
                SELECT * FROM data_check_checkout
            """,
            nativeQuery = true)
    List<Room> getRoomsBooked(LocalDateTime checkin, LocalDateTime checkout);

    @Query("SELECT (COUNT (r) > 0) FROM Room r WHERE r.id = ?1 AND r.deleteFlag = true")
    boolean existsTrashById(Integer roomId);

    @Modifying
    @Transactional
    @Query("UPDATE Room SET deleteFlag = ?2 WHERE id = ?1")
    void updateDeleteFlagById(Integer roomId, Boolean deleteFlag);

    @Query(value = "SELECT r.* FROM room r WHERE r.type LIKE CONCAT('%', :typeRoom, '%') AND r.delete_flag = false",
            nativeQuery = true)
    List<Room> getRoomActiveByType(String typeRoom);

    @Modifying
    @Transactional
    @Query("UPDATE Room SET sale = null WHERE sale.id = ?1")
    void deleteSaleFromRooms(Integer saleId);

}
