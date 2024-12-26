package com.hit.hotel.repository.room.model.projection;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import com.hit.hotel.repository.sale.model.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface RoomDetailPjt {

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

    @Value("#{new com.hit.hotel.repository.sale.model.SaleSummaryDTO(target.saleId, target.saleDayStart, target.saleDayEnd, target.saleSalePercent)}")
    SaleSummaryDTO getRawSale();

    @Value("#{new com.hit.common.core.domain.dto.CreatorDTO(target.creatorId, target.creatorFirstName, target.creatorLastName, target.creatorAvatar)}")
    CreatorDTO getCreator();

    @Value("#{new com.hit.common.core.domain.dto.ModifierDTO(target.modifierId, target.modifierFirstName, target.modifierLastName, target.modifierAvatar)}")
    ModifierDTO getModifier();

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
