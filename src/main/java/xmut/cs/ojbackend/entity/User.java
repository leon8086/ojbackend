package xmut.cs.ojbackend.entity;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *  实体类。
 *
 * @author leon
 * @since 2024-07-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final Integer ADMINTYPE_REGULAR = 9;
    public static final int ADMINTYPE_ADMIN = 2;
    public static final int ADMINTYPE_SUPERADMIN = 1;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String password;

    private Date lastLogin;

    private Date createTime;

    private Date resetPasswordTokenExpireTime;

    private String username;

    private String email;

    @Column(onInsertValue = "9")
    private Integer adminType;

    private String resetPasswordToken;

    @Column(onInsertValue = "false")
    private Boolean isDisabled;

    @Column(onInsertValue = "'/public/avatar/default.png'")
    private String avatar;

    private String realName;

    private String grade;

    @Column(onInsertValue = "false")
    private Boolean firstLogin;

    @Column(onInsertValue = "0")
    private Integer acceptedNumber;

    @Column(onInsertValue = "0")
    private Integer submissionNumber;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject problemsStatus;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
