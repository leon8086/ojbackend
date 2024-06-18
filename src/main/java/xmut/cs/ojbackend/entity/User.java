package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.*;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author leon
 * @since 2024-06-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    @Column(isLarge = true)
    private String password;

    private String lastLogin;

    private String username;

    private String email;

    @Column(onInsertValue = "to_char(now(), 'YYYY-MM-DD HH24:MI:SS')")
    private String createTime;

    @Column(onInsertValue = "'Regular User'")
    private String adminType;

    @Column(onInsertValue = "false")
    private Boolean isDisabled;

    private String problemPermission;

    @RelationOneToOne(selfField = "id", targetField = "userId")
    UserProfile userProfile;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
