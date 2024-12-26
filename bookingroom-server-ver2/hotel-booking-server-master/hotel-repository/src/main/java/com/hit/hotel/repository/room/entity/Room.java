package com.hit.hotel.repository.room.entity;

import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.sale.entity.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
@Entity(name = "Room")
public class Room extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "bed", nullable = false)
    private String bed;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "services", nullable = false)
    private String services;

    @Lob
    @Nationalized
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sale_id", insertable = false, updatable = false)
    private Integer saleId;

    //Link to table Sale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", foreignKey = @ForeignKey(name = "fk_room__sale_id"))
    private Sale sale;

    //Link to table Media
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private Set<Media> medias = new HashSet<>();

}
