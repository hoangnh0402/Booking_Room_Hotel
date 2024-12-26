package com.hit.hotel.repository.service.entity;

import com.hit.common.core.context.UploadFileContext;
import com.hit.hotel.repository.base.FlagUserDateAuditing;
import com.hit.hotel.repository.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Nationalized;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
@Entity(name = "Service")
public class Service extends FlagUserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "price")
    private Integer price;

    @Column(name = "is_pre_book", nullable = false)
    private Boolean isPreBook;

    @Lob
    @Nationalized
    @Column(name = "description", nullable = false)
    private String description;

    //Link to table Product
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "service")
    private Set<Product> products = new HashSet<>();

    @PreRemove
    private void destroyThumbnail() {
        if (ObjectUtils.isNotEmpty(thumbnail)) {
            UploadFileContext.getUploadFileUtils().destroyFileWithUrl(thumbnail);
        }
    }

}
