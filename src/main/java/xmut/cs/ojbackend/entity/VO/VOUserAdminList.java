package xmut.cs.ojbackend.entity.VO;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;
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
public class VOUserAdminList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String password;

    private Date lastLogin;

    private Date createTime;

    private Date resetPasswordTokenExpireTime;

    private String username;

    private String email;

    private Integer adminType;

    private String resetPasswordToken;

    @Column(onInsertValue = "false")
    private Boolean isDisabled;

    @Column(onInsertValue = "'/public/avatar/default.png'")
    private String avatar;

    private String realName;

    private Integer grade;

    @RelationOneToOne(selfField = "grade", targetTable = "grade", targetField = "id", valueField = "name")
    private String gradeName;

    @Column(onInsertValue = "true")
    private Boolean firstLogin;

    @Column(onInsertValue = "0")
    private Integer acceptedNumber;

    @Column(onInsertValue = "0")
    private Integer submissionNumber;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject problemsStatus;

    private Date lastAccept;

    public VOUserAdminList(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
