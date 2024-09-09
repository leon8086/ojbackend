package xmut.cs.ojbackend.entity.VO;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
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
@Table(value = "problem")
public class VOProblemDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private String inputDescription;

    private String outputDescription;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray samples;

    private String hint;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> languages;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject template;

    private Integer timeLimit;

    private Integer memoryLimit;

    private String difficulty;

    @Column(onInsertValue = "0")
    private Long submissionNumber;

    @Column(onInsertValue = "0")
    private Long acceptedNumber;

    private String displayId;

    private Boolean visible;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject statisticInfo;

    private Integer totalScore;

    private Boolean isPublic;

    @Column(onInsertValue = "null")
    private Integer majorTagId;

    @Column(onInsertValue = "null")
    private Integer subTagId;

    @RelationOneToOne(selfField = "majorTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String majorTag;

    @RelationOneToOne(selfField = "subTagId", targetTable="problem_tag", targetField="id", valueField = "name")
    private String subTag;

    @Column(ignore = true)
    private List<VOSubmissionDetail> submissionList;

    @Column(ignore = true)
    private Integer status;
}
