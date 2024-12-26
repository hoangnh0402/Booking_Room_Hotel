package com.hit.hotel.repository.media.adapter;

import com.hit.common.core.context.UploadFileContext;
import com.hit.common.utils.UploadFileUtils;
import com.hit.hotel.repository.media.MediaStore;
import com.hit.hotel.repository.media.constants.MediaType;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.media.repository.MediaRepository;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class MediaAdapter extends BaseJPAAdapter<Media, Integer, MediaRepository> implements MediaStore {

    @Override
    protected Class<Media> getEntityClass() {
        return Media.class;
    }

    @Override
    public List<Media> getMediaActiveInRoomIds(Collection<Integer> roomIds) {
        return this.repository.getMediaActiveInRoomIds(roomIds);
    }

    @Override
    public List<Media> getMediaActiveByRoomId(Integer roomId) {
        return this.repository.getMediaActiveByRoomId(roomId);
    }

    @Override
    public Set<Media> createMediaByRoom(Room room, List<MultipartFile> files) {
        Set<Media> medias = new HashSet<>();
        UploadFileUtils uploadFileUtils = UploadFileContext.getUploadFileUtils();
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (Objects.requireNonNull(contentType).equals("video/mp4")) {
                Media video = new Media();
                video.setUrl(uploadFileUtils.uploadFile(file));
                video.setType(MediaType.VIDEO.name());
                video.setRoom(room);
                medias.add(this.save(video));
            } else {
                Media image = new Media();
                image.setUrl(uploadFileUtils.uploadFile(file));
                image.setType(MediaType.IMAGE.name());
                image.setRoom(room);
                medias.add(this.save(image));
            }
        }
        return medias;
    }

    @Override
    public void deleteMediaByRoomIdAndMediaIdIn(Integer roomId, List<Integer> mediaIds) {
        this.repository.updateDeleteFlagByRoomIdAndMediaIdIn(roomId, mediaIds, Boolean.TRUE);
    }

}
