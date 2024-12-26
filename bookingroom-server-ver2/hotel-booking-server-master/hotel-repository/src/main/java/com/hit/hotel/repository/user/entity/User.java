package com.hit.hotel.repository.user.entity;

import com.hit.hotel.repository.base.DateAuditing;
import com.hit.hotel.repository.role.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity(name = "User")
public class User extends DateAuditing {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Nationalized
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Nationalized
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Nationalized
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Nationalized
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    //Link to table Role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user__role_id"))
    private Role role;

    @PrePersist
    public void prePersist() {
        if (this.isEnable == null) {
            this.isEnable = Boolean.FALSE;
        }
        if (this.isLocked == null) {
            this.isLocked = Boolean.FALSE;
        }
    }

}
