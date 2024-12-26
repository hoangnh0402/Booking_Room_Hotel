package com.hit.hotel.repository.bookingdetail.model;

import java.time.LocalDateTime;

public interface StatisticRoomBookedProjection {

    Integer getId();

    String getName();

    Integer getPrice();

    String getType();

    String getBed();

    Integer getSize();

    Integer getCapacity();

    String getServices();

    String getDescription();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    Integer getValue();

}
