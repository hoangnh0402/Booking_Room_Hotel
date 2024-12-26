package com.hit.hotel.repository.room.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import com.hit.hotel.repository.room.repository.RoomRepository;
import com.hit.jpa.BaseJPAAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Component
public class RoomAdapter extends BaseJPAAdapter<Room, Integer, RoomRepository> implements RoomStore {

    @Override
    protected Class<Room> getEntityClass() {
        return Room.class;
    }

    @Override
    public RoomDetailPjt getRoomDetailPjt(Integer roomId) {
        return this.repository.getRoomDetailPjtActive(roomId);
    }

    @Override
    public PaginationResponse<RoomDetailPjt> getRoomDetailPjts(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<RoomDetailPjt> rooms = this.repository.getRoomDetailPjts(request, pageable);
        return new PaginationResponse<>(request, rooms);
    }

    @Override
    public PaginationResponse<RoomAvailablePjt> getRoomAvailablePjts(PaginationRequest request,
                                                                     RoomFilterCondition condition) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<RoomAvailablePjt> rooms = this.repository.getRoomAvailablePjts(request, condition, pageable);
        return new PaginationResponse<>(request, rooms);
    }

    @Override
    public PaginationResponse<RoomDetailAvailablePjt> getRoomDetailAvailablePjts(PaginationRequest request,
                                                                                 RoomFilterCondition condition) {
        Pageable pageable = PaginationUtils.buildPageableNative(request);
        Page<RoomDetailAvailablePjt> rooms = this.repository.getRoomDetailAvailablePjts(request, condition, pageable);
        return new PaginationResponse<>(request, rooms);
    }

    @Override
    public List<Room> getRoomsBooked(LocalDateTime checkin, LocalDateTime checkout) {
        return this.repository.getRoomsBooked(checkin, checkout);
    }

    @Override
    public boolean existsTrash(Integer roomId) {
        return this.repository.existsTrashById(roomId);
    }

    @Override
    public void deleteToTrashById(Integer roomId) {
        this.repository.updateDeleteFlagById(roomId, Boolean.TRUE);
    }

    @Override
    public void deleteTrashById(Integer roomId) {
        this.delete(roomId);
    }

    @Override
    public void restoreTrashById(Integer roomId) {
        this.repository.updateDeleteFlagById(roomId, Boolean.FALSE);
    }

    @Override
    public List<Room> getRoomsByType(String type) {
        return this.repository.getRoomActiveByType(type);
    }

    @Override
    public void deleteSaleFromRooms(Integer saleId) {
        this.repository.deleteSaleFromRooms(saleId);
    }

}
