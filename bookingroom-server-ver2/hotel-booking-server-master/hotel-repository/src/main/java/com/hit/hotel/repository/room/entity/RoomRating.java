package com.hit.hotel.repository.room.entity;

import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_rating")
@Entity(name = "RoomRating")
public class RoomRating extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "star", nullable = false)
    private Integer star;

    @Lob
    @Nationalized
    @Column(name = "comment")
    private String comment;

    //Link to table Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "fk_room_rating__room_id"))
    private Room room;

    //Link to table User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_room_rating__user_id"))
    private User user;

}
