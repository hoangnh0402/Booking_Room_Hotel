package com.hit.hotel.repository.media.entity;

import com.hit.common.core.context.UploadFileContext;
import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.room.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media")
@Entity(name = "Media")
public class Media extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "room_id", insertable = false, updatable = false)
    private Integer roomId;

    //Link to table Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "fk_media__room_id"))
    private Room room;

    @PreRemove
    private void destroyUrlMedia() {
        if (ObjectUtils.isNotEmpty(url)) {
            UploadFileContext.getUploadFileUtils().destroyFileWithUrl(url);
        }
    }
}
