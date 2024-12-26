package com.hit.hotel.repository.media;

import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.room.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MediaStore {

    List<Media> getMediaActiveInRoomIds(Collection<Integer> roomIds);

    List<Media> getMediaActiveByRoomId(Integer roomId);

    Set<Media> createMediaByRoom(Room room, List<MultipartFile> files);

    void deleteMediaByRoomIdAndMediaIdIn(Integer roomId, List<Integer> mediaIds);

}
