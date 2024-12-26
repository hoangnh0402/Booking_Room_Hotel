package com.hit.hotel.repository.booking.repository;

import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.booking.model.condition.BookingFilterCondition;
import com.hit.hotel.repository.booking.model.condition.StatisticRevenueCondition;
import com.hit.hotel.repository.booking.model.projection.StatisticBookingStatusProjection;
import com.hit.hotel.repository.booking.model.projection.StatisticCustomerTopBookingProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends BaseJPARepository<Booking, Integer> {

    @EntityGraph(attributePaths = {"user", "bookingRoomDetails", "bookingServiceDetails", "bookingRoomDetails.room",
            "bookingRoomDetails.room.medias", "bookingServiceDetails.service"})
    @Query(value = """
                SELECT b
                FROM Booking b
                WHERE b.id = :id
            """)
    Booking getBookingDetail(Integer id);

    @EntityGraph(attributePaths = {"user", "bookingRoomDetails", "bookingServiceDetails"})
    @Query(value = """
                SELECT b
                FROM Booking b
                WHERE b.user.id = :bookerId
            """)
    Page<Booking> getBookingsByBookerId(String bookerId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "bookingRoomDetails", "bookingServiceDetails"})
    @Query(value = """
                SELECT b
                FROM Booking b
                WHERE (:#{#condition.checkin} IS NULL OR b.expectedCheckIn >= :#{#condition.checkin})
                    AND (:#{#condition.checkout} IS NULL OR b.expectedCheckOut <= :#{#condition.checkout})
                    AND (:#{#condition.bookingStatus} IS NULL OR b.status = :#{#condition.bookingStatus})
            """, countQuery = """
                SELECT COUNT(*)
                FROM Booking b
                WHERE (:#{#condition.checkin} IS NULL OR b.expectedCheckIn >= :#{#condition.checkin})
                    AND (:#{#condition.checkout} IS NULL OR b.expectedCheckOut <= :#{#condition.checkout})
                    AND (:#{#condition.bookingStatus} IS NULL OR b.status = :#{#condition.bookingStatus})
            """)
    Page<Booking> getBookings(BookingFilterCondition condition, Pageable pageable);

    @Query(value = """
                SELECT u.id                 AS id,
                       u.email              AS email,
                       u.phone_number       AS phoneNumber,
                       u.first_name         AS firstName,
                       u.last_name          AS lastName,
                       u.gender             AS gender,
                       u.birthday           AS birthday,
                       u.address            AS address,
                       u.avatar             AS avatar,
                       u.is_enable          AS isEnable,
                       u.is_locked          AS isLocked,
                       r.name               AS roleName,
                       u.created_date       AS createdDate,
                       u.last_modified_date AS lastModifiedDate,
                       COUNT(b.id)          AS `value`
                FROM user u
                         INNER JOIN role r ON r.id = u.role_id
                         INNER JOIN booking b on b.user_id = u.id
                WHERE r.name = 'USER'
                  AND u.is_enable = 1
                  AND u.is_locked = 0
                  AND b.status NOT IN ('PENDING', 'CANCEL')
                GROUP BY u.id
                ORDER BY COUNT(b.id) DESC
                LIMIT 10
            """,
            nativeQuery = true)
    List<StatisticCustomerTopBookingProjection> findAllCustomerTopBooking();

    @Query(value = """
                SELECT *
                FROM booking b
                WHERE MONTH(b.created_date) BETWEEN :#{#condition.fromMonth} AND :#{#condition.toMonth}
                    AND YEAR(b.created_date) = :#{#condition.year}
                    AND b.status = 'CHECKED_OUT'
            """, nativeQuery = true)
    List<Booking> statisticRevenue(StatisticRevenueCondition condition);

    @Query(value = """
                SELECT b.status AS status,
                       COUNT(*) AS `value`
                FROM booking b
                WHERE (:month IS NULL OR MONTH(b.expected_check_in) = :month)
                    AND YEAR(b.expected_check_out) = :year
                    AND b.status in :status
                GROUP BY b.status
            """,
            nativeQuery = true)
    List<StatisticBookingStatusProjection> statisticBookingByMonthAndYearAndStatusIn(Integer month, Integer year,
                                                                                     List<String> status);

}
