package com.jung9928.sns.model.entity;

import com.jung9928.sns.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")       // PostgreSQL의 default 제공 user 테이블이 아닌 내가 생성한 user 테이블로 접근을 위해 "\" 사용.
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", unique = true)
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // 삭제된 시간을 저장할 컬럼을 지정한 이유
    // => 유저가 탈퇴 시, 유저 계정정보를 바로 삭제하는 것이 아니라 임시로 보관해야 할 경우도 필요(추후, 회원이 복구해달라 할 시)
    // 즉, 유저가 탈퇴요청 시, 탈퇴 요청을 받는 시간을 저장하는 컬럼임.
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static UserEntity of(String userName, String password) {
        UserEntity entity = new UserEntity();
        entity.setUserName(userName);
        entity.setPassword(password);

        return entity;
    }
}
