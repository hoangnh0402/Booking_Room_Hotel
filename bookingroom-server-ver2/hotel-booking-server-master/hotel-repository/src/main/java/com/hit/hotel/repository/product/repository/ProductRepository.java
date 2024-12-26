package com.hit.hotel.repository.product.repository;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.product.model.ProductDetailProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends BaseJPARepository<Product, Integer> {

    @Query(value = """
                SELECT *
                FROM product p
                WHERE (
                        COALESCE(:#{#request.keyword}, '') = ''
                        OR p.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND p.service_id = :serviceId
                    AND p.delete_flag = :#{#request.deleteFlag}
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM product p
                        WHERE (
                                COALESCE(:#{#request.keyword}, '') = ''
                                OR p.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                            AND p.service_id = :serviceId
                            AND p.delete_flag = :#{#request.deleteFlag}
                    """,
            nativeQuery = true)
    Page<Product> getProductsByServiceId(Integer serviceId, PaginationRequest request, Pageable pageable);

    @Query(value = """
                SELECT p.id                 AS id,
                       p.name               AS name,
                       p.thumbnail          AS thumbnail,
                       p.description        AS description,
                       p.created_date       AS createdDate,
                       p.last_modified_date AS lastModifiedDate,
                       s.id                 AS serviceId,
                       s.title              AS serviceTitle,
                       s.thumbnail          AS serviceThumbnail,
                       s.price              AS servicePrice,
                       creator.id           AS creatorId,
                       creator.first_name   AS creatorFirstName,
                       creator.last_name    AS creatorLastName,
                       creator.avatar       AS creatorAvatar,
                       modifier.id          AS modifierId,
                       modifier.first_name  AS modifierFirstName,
                       modifier.last_name   AS modifierLastName,
                       modifier.avatar      AS modifierAvatar
                FROM product p
                    LEFT JOIN user creator ON p.created_by = creator.id
                    LEFT JOIN user modifier ON p.last_modified_by = modifier.id
                    LEFT JOIN service s ON s.id = p.service_id
                WHERE (
                        COALESCE(:#{#request.keyword}, '') = ''
                        OR p.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                    AND p.delete_flag = :#{#request.deleteFlag}
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM product p
                            LEFT JOIN user creator ON p.created_by = creator.id
                            LEFT JOIN user modifier ON p.last_modified_by = modifier.id
                            LEFT JOIN service s ON s.id = p.service_id
                        WHERE (
                                COALESCE(:#{#request.keyword}, '') = ''
                                OR p.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                            AND p.delete_flag = :#{#request.deleteFlag}
                    """,
            nativeQuery = true)
    Page<ProductDetailProjection> getProductDetails(PaginationRequest request, Pageable pageable);

    @Query(value = """
                SELECT p.id                 AS id,
                       p.name               AS name,
                       p.thumbnail          AS thumbnail,
                       p.description        AS description,
                       p.created_date       AS createdDate,
                       p.last_modified_date AS lastModifiedDate,
                       s.id                 AS serviceId,
                       s.title              AS serviceTitle,
                       s.thumbnail          AS serviceThumbnail,
                       s.price              AS servicePrice,
                       creator.id           AS creatorId,
                       creator.first_name   AS creatorFirstName,
                       creator.last_name    AS creatorLastName,
                       creator.avatar       AS creatorAvatar,
                       modifier.id          AS modifierId,
                       modifier.first_name  AS modifierFirstName,
                       modifier.last_name   AS modifierLastName,
                       modifier.avatar      AS modifierAvatar
                FROM product p
                    LEFT JOIN user creator ON p.created_by = creator.id
                    LEFT JOIN user modifier ON p.last_modified_by = modifier.id
                    LEFT JOIN service s ON s.id = p.service_id
                WHERE p.id = :id
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM product p
                            LEFT JOIN user creator ON p.created_by = creator.id
                            LEFT JOIN user modifier ON p.last_modified_by = modifier.id
                            LEFT JOIN service s ON s.id = p.service_id
                        WHERE p.id = :id
                    """,
            nativeQuery = true)
    ProductDetailProjection getProductDetail(Integer id);

    @Query("SELECT (COUNT (s) > 0) FROM Product s WHERE s.id = ?1 AND s.deleteFlag = true")
    boolean existsTrashById(Integer productId);

    @Modifying
    @Transactional
    @Query("UPDATE Product SET deleteFlag = ?2 WHERE id = ?1")
    void updateDeleteFlagById(Integer productId, Boolean deleteFlag);

}
