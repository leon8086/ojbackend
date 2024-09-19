package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class VOProblemStatistic implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String difficulty;

    private String displayId;

    @Column(onInsertValue = "null")
    private Integer majorTagId;

    @Column(onInsertValue = "null")
    private Integer subTagId;

    @RelationOneToOne(selfField = "majorTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String majorTag;

    @RelationOneToOne(selfField = "subTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String subTag;
}