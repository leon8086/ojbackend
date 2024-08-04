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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class VOProblemAdminList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject template;

    private Date createTime;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Boolean visible;

    private String difficulty;

    private Long submissionNumber;

    private Long acceptedNumber;

    private Integer createdById;

    private String displayId;

    private Integer totalScore;

    @Column(onInsertValue = "null")
    private Integer majorTagId;

    @Column(onInsertValue = "null")
    private Integer subTagId;

    @RelationOneToOne(selfField = "majorTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String majorTag;

    @RelationOneToOne(selfField = "subTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String subTag;
}