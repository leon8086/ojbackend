package xmut.cs.ojbackend.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;

import java.io.Serializable;
import java.util.List;

import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private String inputDescription;

    private String outputDescription;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray samples;

    private String testCaseId;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray testCaseScore;

    private String hint;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> languages;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject template;

    @Column(onInsertValue = "to_char(now(), 'YYYY-MM-DD HH24:MI:SS')")
    private String createTime;

    private String lastUpdateTime;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Boolean spj;

    private String spjLanguage;

    private String spjCode;

    private String spjVersion;

    private String ruleType;

    private Boolean visible;

    private String difficulty;

    private String source;

    @Column(onInsertValue = "0")
    private Long submissionNumber;

    @Column(onInsertValue = "0")
    private Long acceptedNumber;

    private Integer createdById;

    @Column(value = "_id")
    private String displayId;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject statisticInfo;

    private Integer totalScore;

    private Integer contestId;

    private Boolean isPublic;

    private Boolean spjCompileOk;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONObject ioMode;

    private Boolean shareSubmission;

    @RelationManyToMany(
            joinTable = "problem_tags",
            selfField = "id",
            joinSelfColumn="problem_id",
            targetField = "id",
            joinTargetColumn="problemtag_id"
    )
    private List<ProblemTag> tags;
}