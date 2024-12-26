package com.hit.hotel.repository.media.repository;

import com.hit.hotel.repository.media.entity.Media;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface MediaRepository extends BaseJPARepository<Media, Integer> {

    @Query("SELECT m FROM Media m WHERE m.room.id in ?1 AND m.deleteFlag = false")
    List<Media> getMediaActiveInRoomIds(Collection<Integer> roomIds);

    @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
    List<Media> getMediaActiveByRoomId(Integer roomId);

    @Modifying
    @Transactional
    @Query("UPDATE Media SET deleteFlag = ?3 WHERE roomId = ?1 AND id IN ?2")
    void updateDeleteFlagByRoomIdAndMediaIdIn(Integer roomId, List<Integer> mediaIds, Boolean deleteFlag);

}
