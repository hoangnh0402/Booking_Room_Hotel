package com.hit.hotel.repository.room.model.projection;

import com.hit.hotel.repository.sale.model.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;

public interface RoomAvailablePjt {

    Integer getId();

    String getName();

    Integer getPrice();

    String getType();

    String getBed();

    Integer getSize();

    Integer getCapacity();

    String getServices();

    String getDescription();

    BigInteger getIsAvailable();

    @Value("#{new com.hit.hotel.repository.sale.model.SaleSummaryDTO(target.saleId, target.saleDayStart, target.saleDayEnd, target.saleSalePercent)}")
    SaleSummaryDTO getRawSale();

    default SaleSummaryDTO getSale() {
        SaleSummaryDTO sale = this.getRawSale();
        if (sale != null &&
                sale.getId() == null &&
                sale.getDayStart() == null &&
                sale.getDayEnd() == null &&
                sale.getSalePercent() == null) {
            return null;
        }
        return sale;
    }

}
