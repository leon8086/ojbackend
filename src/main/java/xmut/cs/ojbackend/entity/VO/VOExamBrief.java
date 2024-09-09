package xmut.cs.ojbackend.entity.VO;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.annotation.Table;
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
 * @since 2024-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "exam")
public class VOExamBrief implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private Integer courseId;

    @RelationOneToOne( selfField = "courseId", targetTable = "course", targetField = "id", valueField = "courseName")
    private String course;

    private Boolean isEnded;

    private Integer problemCount;
}
