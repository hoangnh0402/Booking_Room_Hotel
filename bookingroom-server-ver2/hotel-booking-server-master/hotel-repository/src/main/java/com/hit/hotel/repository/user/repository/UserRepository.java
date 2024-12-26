package com.hit.hotel.repository.user.repository;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.hotel.repository.user.entity.User;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseJPARepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.phoneNumber = ?2")
    Optional<User> getUserByEmailOrPhoneNumber(String email, String phone);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> getUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User SET isEnable = true WHERE email = ?1")
    Integer enableUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User SET password = ?2 WHERE email = ?1")
    void updatePasswordByEmail(String email, String password);

    @Modifying
    @Transactional
    @Query("UPDATE User SET isLocked = ?2 WHERE id = ?1")
    void updateIsLockedById(String id, Boolean isLocked);

    @Query("SELECT (COUNT (u) > 0) FROM User u WHERE u.id = ?1 AND u.isLocked = true")
    boolean existsUserLockedById(String userId);

    @Query(value = """
                SELECT u.*
                FROM user u
                         INNER JOIN role r ON r.id = u.role_id
                WHERE u.is_enable = :isEnable AND u.is_locked = :isLocked AND r.name = :roleName
                  AND (
                    COALESCE(:#{#request.keyword}, '') = ''
                        OR u.last_name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR u.first_name LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR u.email LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR u.phone_number LIKE CONCAT('%', :#{#request.keyword}, '%')
                        OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                    )
            """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM user u
                                 INNER JOIN role r ON r.id = u.role_id
                        WHERE u.is_enable = :isEnable AND u.is_locked = :isLocked AND r.name = :roleName
                          AND (
                            COALESCE(:#{#request.keyword}, '') = ''
                                OR u.last_name LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR u.first_name LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR u.email LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR u.phone_number LIKE CONCAT('%', :#{#request.keyword}, '%')
                                OR r.name LIKE CONCAT('%', :#{#request.keyword}, '%')
                            )
                    """,
            nativeQuery = true)
    Page<User> getUsers(PaginationRequest request, Boolean isEnable, Boolean isLocked, String roleName, Pageable pageable);


}
