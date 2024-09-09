package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.RelationOneToOne;
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
public class VOUserCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private Integer adminType;

    private Boolean isDisabled;

    private String realName;

    private Integer grade;

    @RelationOneToOne(selfField = "grade", targetTable = "grade", targetField = "id", valueField = "name")
    private String gradeName;

    private String email;
}
