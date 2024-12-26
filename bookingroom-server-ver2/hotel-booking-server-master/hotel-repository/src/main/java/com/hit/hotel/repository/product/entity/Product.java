package com.hit.hotel.repository.product.entity;

import com.hit.common.core.context.UploadFileContext;
import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.service.entity.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity(name = "Product")
public class Product extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Lob
    @Nationalized
    @Column(name = "description")
    private String description;

    //Link to table Service
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "fk_product__service_id"))
    private Service service;

    @PreRemove
    private void destroyThumbnail() {
        if (ObjectUtils.isNotEmpty(thumbnail)) {
            UploadFileContext.getUploadFileUtils().destroyFileWithUrl(thumbnail);
        }
    }

}
