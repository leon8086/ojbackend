package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
@Table(value = "course")
public class VOCourseList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String courseName;

    private Integer ownerId;

    @RelationOneToOne(selfField = "ownerId", targetField = "id", targetTable = "user", valueField="username")
    private String owner;

    @RelationManyToMany(
        joinTable = "course_user",
        selfField = "id",
        joinSelfColumn="course_id",
        targetField = "id",
        joinTargetColumn="user_id"
    )
    private List<VOUserCourse> students;

    private Boolean isClosed;

}
