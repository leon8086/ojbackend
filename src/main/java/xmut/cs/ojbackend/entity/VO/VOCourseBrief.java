package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
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
@Table(value = "course")
public class VOCourseBrief implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String courseName;

    private Integer ownerId;

    @Column(ignore = true)
    @RelationOneToOne(selfField = "ownerId", targetField = "id", targetTable = "user", valueField = "username")
    private String owner;

    private Boolean isClosed;
}