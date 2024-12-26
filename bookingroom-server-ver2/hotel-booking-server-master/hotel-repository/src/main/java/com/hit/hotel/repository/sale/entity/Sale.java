package com.hit.hotel.repository.sale.entity;

import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.room.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
@Entity(name = "Sale")
public class Sale extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "day_start", nullable = false)
    private LocalDateTime dayStart;

    @Column(name = "day_end", nullable = false)
    private LocalDateTime dayEnd;

    @Column(name = "sale_percent", nullable = false)
    private Float salePercent;

    //Link to table Room
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "sale")
    private Set<Room> rooms = new HashSet<>();

    @PreRemove
    private void removeSaleFromRooms() {
        for (Room room : rooms) {
            room.setSale(null);
        }
    }

}
