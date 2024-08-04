package xmut.cs.ojbackend.entity.DTO;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class DTOUserAdminUpdate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String password;

    private Integer adminType;

    private Boolean isDisabled;

    private String realName;

    private String email;

    private String grade;
}
