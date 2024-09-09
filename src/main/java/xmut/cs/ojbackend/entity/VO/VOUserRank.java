package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xmut.cs.ojbackend.entity.Score;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class VOUserRank implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String username;

    private String realName;

    private Integer grade;

    @Column(onInsertValue = "0")
    private Integer acceptedNumber;

    @Column(onInsertValue = "0")
    private Integer submissionNumber;

    private Date lastAccept;

    @RelationOneToOne(selfField = "grade", targetTable = "grade", targetField = "id", valueField = "name")
    private String gradeName;

    @RelationOneToMany(selfField = "id", targetTable = "score", targetField = "userId")
    private List<Score> scoreList;

    @Column(ignore = true)
    private Integer score;
}
