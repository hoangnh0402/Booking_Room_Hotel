package com.hit.hotel.common.domain.mapper;

import com.hit.hotel.common.domain.response.booking.*;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>CommonImpl")
public abstract class BookingMapper {

    public abstract Booking copy(Booking booking);

    @Mapping(target = "booker", source = "booking.user")
    public abstract BookingResponse toBookingResponse(Booking booking,
                                                      Double totalRoomPrice, Double totalServicePrice,
                                                      Long totalSurcharge);

    @Mapping(target = "booker", source = "booking.user")
    @Mapping(target = "rooms", expression = "java(toBookingRoomDetailResponses(booking.getBookingRoomDetails()))")
    @Mapping(target = "services", expression = "java(toBookingServiceDetailResponses(booking.getBookingServiceDetails" +
            "()))")
    public abstract BookingDetailResponse toBookingDetailResponse(Booking booking,
                                                                  List<BookingSurchargeResponse> bookingSurcharge,
                                                                  Double totalRoomPrice, Double totalServicePrice,
                                                                  Long totalSurcharge);

    public Set<BookingRoomDetailResponse> toBookingRoomDetailResponses(Set<BookingRoomDetail> bookingRoomDetails) {
        if (CollectionUtils.isEmpty(bookingRoomDetails)) {
            return Collections.emptySet();
        }
        Set<BookingRoomDetailResponse> result = new HashSet<>();
        for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
            BookingRoomDetailResponse bookingRoomRes = new BookingRoomDetailResponse();
            bookingRoomRes.setBookingPrice(bookingRoomDetail.getPrice());
            bookingRoomRes.setSalePercent(bookingRoomDetail.getSalePercent());
            bookingRoomRes.setRoom(this.toBookingRoomInfoResponse(bookingRoomDetail.getRoom()));
            result.add(bookingRoomRes);
        }
        return result;
    }

    public abstract BookingRoomDetailResponse.RoomInfoResponse toBookingRoomInfoResponse(Room room);

    public Set<BookingServiceDetailResponse> toBookingServiceDetailResponses(Set<BookingServiceDetail> bookingServiceDetails) {
        if (CollectionUtils.isEmpty(bookingServiceDetails)) {
            return Collections.emptySet();
        }
        Set<BookingServiceDetailResponse> result = new HashSet<>();
        for (BookingServiceDetail bookingServiceDetail : bookingServiceDetails) {
            BookingServiceDetailResponse bookingServiceRes = new BookingServiceDetailResponse();
            bookingServiceRes.setService(this.toBookingServiceInfoResponse(bookingServiceDetail.getService()));
            bookingServiceRes.setQuantity(bookingServiceDetail.getQuantity());
            bookingServiceRes.setBookingPrice(bookingServiceDetail.getPrice());
            result.add(bookingServiceRes);
        }
        return result;
    }

    public abstract BookingServiceDetailResponse.ServiceInfoResponse toBookingServiceInfoResponse(Service service);

    public abstract BookerResponse toBookerResponse(User user);

}
