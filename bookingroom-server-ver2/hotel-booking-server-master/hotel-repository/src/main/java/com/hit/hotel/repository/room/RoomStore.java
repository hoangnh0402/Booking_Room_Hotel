package com.hit.hotel.repository.room;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoomStore {

    Room get(Integer roomId);

    List<Room> getAllInIds(Collection<Integer> ids);

    Map<Integer, Room> getMapId(Collection<Integer> ids);

    Room save(Room room);

    List<Room> saveAll(Collection<Room> rooms);

    Room update(Room room);

    boolean exists(Integer roomId);

    RoomDetailPjt getRoomDetailPjt(Integer roomId);

    PaginationResponse<RoomDetailPjt> getRoomDetailPjts(PaginationRequest request);

    PaginationResponse<RoomAvailablePjt> getRoomAvailablePjts(PaginationRequest request, RoomFilterCondition condition);

    PaginationResponse<RoomDetailAvailablePjt> getRoomDetailAvailablePjts(PaginationRequest request,
                                                                          RoomFilterCondition condition);

    List<Room> getRoomsBooked(LocalDateTime checkin, LocalDateTime checkout);

    boolean existsTrash(Integer roomId);

    void deleteToTrashById(Integer roomId);

    void deleteTrashById(Integer roomId);

    void restoreTrashById(Integer roomId);

    List<Room> getRoomsByType(String type);

    void deleteSaleFromRooms(Integer saleId);

}
