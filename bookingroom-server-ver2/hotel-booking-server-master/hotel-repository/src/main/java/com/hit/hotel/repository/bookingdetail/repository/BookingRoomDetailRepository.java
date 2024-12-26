package com.hit.hotel.repository.bookingdetail.repository;

import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.model.StatisticRoomBookedProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookingRoomDetailRepository extends BaseJPARepository<BookingRoomDetail, Integer> {

    @Query("SELECT brd FROM BookingRoomDetail brd WHERE brd.booking.id = ?1")
    Set<BookingRoomDetail> getAllByBookingId(Integer id);

    @Query(value = "SELECT r.id, r.name, r.price, r.type, r.bed, r.size, r.capacity, r.services, r.description, " +
            "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, COUNT(b.id) AS `value` " +
            "FROM room r " +
            "LEFT JOIN booking_room_detail brd ON brd.room_id = r.id " +
            "LEFT JOIN booking b ON b.id = brd.booking_id " +
            "WHERE (b.id IS NULL OR (MONTH(b.created_date) = :month AND YEAR(b.created_date) = :year)) " +
            "AND ( " +
            "COALESCE(:keyword, '') = '' " +
            "OR r.name LIKE CONCAT('%', :keyword, '%') " +
            "OR r.type LIKE CONCAT('%', :keyword, '%') " +
            "OR r.price LIKE CONCAT('%', :keyword, '%') " +
            ")" +
            "GROUP BY r.id " +
            "ORDER BY " +
            "CASE WHEN :sortType = 'ASC' THEN COUNT(b.id) END ASC, " +
            "CASE WHEN :sortType = 'DESC' THEN COUNT(b.id) END DESC",
            countQuery = "SELECT COUNT(*) FROM room r " +
                    "LEFT JOIN booking_room_detail brd ON brd.room_id = r.id " +
                    "LEFT JOIN booking b ON b.id = brd.booking_id " +
                    "WHERE (b.id IS NULL OR (MONTH(b.created_date) = :month AND YEAR(b.created_date) = :year)) " +
                    "AND ( " +
                    "COALESCE(:keyword, '') = '' " +
                    "OR r.name LIKE CONCAT('%', :keyword, '%') " +
                    "OR r.type LIKE CONCAT('%', :keyword, '%') " +
                    "OR r.price LIKE CONCAT('%', :keyword, '%') " +
                    ") " +
                    "ORDER BY " +
                    "CASE WHEN :sortType = 'ASC' THEN COUNT(b.id) END ASC, " +
                    "CASE WHEN :sortType = 'DESC' THEN COUNT(b.id) END DESC",
            nativeQuery = true)
    Page<StatisticRoomBookedProjection> statisticRoomBooked(Integer month, Integer year,
                                                            String keyword, String sortType,
                                                            Pageable pageable);

}
