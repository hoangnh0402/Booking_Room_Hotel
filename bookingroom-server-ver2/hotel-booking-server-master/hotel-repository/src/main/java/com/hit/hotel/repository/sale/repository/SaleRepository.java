package com.hit.hotel.repository.sale.repository;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.sale.model.projection.SaleDetailProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SaleRepository extends BaseJPARepository<Sale, Integer> {

    @Query(value = """
                SELECT s.id,
                       s.day_start          AS dayStart,
                       s.day_end            AS dayEnd,
                       s.sale_percent       AS salePercent,
                       s.created_date       AS createdDate,
                       s.last_modified_date AS lastModifiedDate,
                       creator.id           AS creatorId,
                       creator.first_name   AS creatorFirstName,
                       creator.last_name    AS creatorLastName,
                       creator.avatar       AS creatorAvatar,
                       modifier.id          AS modifierId,
                       modifier.first_name  AS modifierFirstName,
                       modifier.last_name   AS modifierLastName,
                       modifier.avatar      AS modifierAvatar
                FROM sale s
                         LEFT JOIN user creator ON s.created_by = creator.id
                         LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                WHERE (
                        COALESCE(:#{#request.keyword}, '') = ''
                        OR s.sale_percent LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
                  AND S.delete_flag = :#{#request.deleteFlag}
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM sale s
                                 LEFT JOIN user creator ON s.created_by = creator.id
                                 LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                        WHERE (
                                COALESCE(:#{#request.keyword}, '') = ''
                                OR s.sale_percent LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                          AND S.delete_flag = :#{#request.deleteFlag}
                    """,
            nativeQuery = true)
    Page<SaleDetailProjection> getSaleDetails(PaginationRequest request, Pageable pageable);

    @Query(value = """
                SELECT s.id,
                       s.day_start          AS dayStart,
                       s.day_end            AS dayEnd,
                       s.sale_percent       AS salePercent,
                       s.created_date       AS createdDate,
                       s.last_modified_date AS lastModifiedDate,
                       creator.id           AS creatorId,
                       creator.first_name   AS creatorFirstName,
                       creator.last_name    AS creatorLastName,
                       creator.avatar       AS creatorAvatar,
                       modifier.id          AS modifierId,
                       modifier.first_name  AS modifierFirstName,
                       modifier.last_name   AS modifierLastName,
                       modifier.avatar      AS modifierAvatar
                FROM sale s
                         LEFT JOIN user creator ON s.created_by = creator.id
                         LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                WHERE s.id = :id
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM sale s
                                 LEFT JOIN user creator ON s.created_by = creator.id
                                 LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                        WHERE s.id = :id
                    """,
            nativeQuery = true)
    SaleDetailProjection getSaleDetail(Integer id);

    @Query("SELECT (COUNT (s) > 0) FROM Sale s WHERE s.id = ?1 AND s.deleteFlag = true")
    boolean existsTrashById(Integer saleId);

    @Modifying
    @Transactional
    @Query("UPDATE Sale SET deleteFlag = ?2 WHERE id = ?1")
    void updateDeleteFlagById(Integer saleId, Boolean deleteFlag);

}
