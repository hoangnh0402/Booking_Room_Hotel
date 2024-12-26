package com.hit.hotel.repository.service.repository;

import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.service.model.projection.ServiceDetailProjection;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServiceRepository extends BaseJPARepository<Service, Integer> {

    @Query(value = """
                SELECT *
                FROM service s
                WHERE s.delete_flag = :deleteFlag
                    AND s.is_pre_book = :isPreBook
                    AND (
                        COALESCE(:keyword, '') = ''
                        OR s.title LIKE CONCAT('%', :keyword, '%')
                        OR s.price LIKE CONCAT('%', :keyword, '%')
                    )
            """,
            countQuery = """
                SELECT COUNT(*)
                FROM service s
                WHERE s.delete_flag = :deleteFlag
                    AND s.is_pre_book = :isPreBook
                    AND (
                        COALESCE(:keyword, '') = ''
                        OR s.title LIKE CONCAT('%', :keyword, '%')
                        OR s.price LIKE CONCAT('%', :keyword, '%')
                    )
            """,
            nativeQuery = true)
    Page<Service> getServices(String keyword, Boolean isPreBook, Boolean deleteFlag, Pageable pageable);

    @Query(value = """
                SELECT s.id,
                       s.title,
                       s.thumbnail,
                       s.price,
                       s.description,
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
                FROM service s
                    LEFT JOIN user creator ON s.created_by = creator.id
                    LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                WHERE s.delete_flag = :deleteFlag
                    AND (
                        COALESCE(:keyword, '') = ''
                        OR s.title LIKE CONCAT('%', :keyword, '%')
                        OR s.price LIKE CONCAT('%', :keyword, '%')
                    )
            """,
            countQuery = """
                SELECT COUNT(*)
                FROM service s
                    LEFT JOIN user creator ON s.created_by = creator.id
                    LEFT JOIN user modifier ON s.last_modified_by = modifier.id
                WHERE s.delete_flag = :deleteFlag
                    AND (
                        COALESCE(:keyword, '') = ''
                        OR s.title LIKE CONCAT('%', :keyword, '%')
                        OR s.price LIKE CONCAT('%', :keyword, '%')
                    )
            """,
            nativeQuery = true)
    Page<ServiceDetailProjection> getServiceDetail(String keyword, Boolean deleteFlag, Pageable pageable);

    @Query("SELECT (COUNT (s) > 0) FROM Service s WHERE s.id = ?1 AND s.deleteFlag = true")
    boolean existsTrashById(Integer serviceId);

    @Modifying
    @Transactional
    @Query("UPDATE Service SET deleteFlag = ?2 WHERE id = ?1")
    void updateDeleteFlagById(Integer serviceId, Boolean deleteFlag);

}
