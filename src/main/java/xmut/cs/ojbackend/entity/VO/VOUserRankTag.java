package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
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
public class VOUserRankTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String username;

    private String realName;

    private Integer grade;

    @Column(onInsertValue = "0")
    private Integer acceptedNumber;

    private Integer score;

    private Date lastAccept;

    @RelationOneToOne(selfField = "grade", targetTable = "grade", targetField = "id", valueField = "name")
    private String gradeName;

    @Column(ignore = true)
    private String majorTagName;
}
