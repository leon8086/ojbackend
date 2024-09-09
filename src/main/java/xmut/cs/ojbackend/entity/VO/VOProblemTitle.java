package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class VOProblemTitle implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> languages;

    private String difficulty;

    @Column(onInsertValue = "0")
    private Long submissionNumber;

    @Column(onInsertValue = "0")
    private Long acceptedNumber;

    private String displayId;

    @Column(onInsertValue = "null")
    private Integer majorTagId;

    @Column(onInsertValue = "null")
    private Integer subTagId;

    @RelationOneToOne(selfField = "majorTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String majorTag;

    @RelationOneToOne(selfField = "subTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String subTag;

    @Column(ignore = true)
    private Integer status;
}