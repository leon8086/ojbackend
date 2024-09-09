package xmut.cs.ojbackend.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class Problem implements Serializable {

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

    private String testCaseId;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray testCaseScore;

    private String hint;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> languages;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONObject template;

    private Date createTime;

    private Date lastUpdateTime;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Boolean visible;

    private String difficulty;

    private String source;

    @Column(onInsertValue = "0")
    private Long submissionNumber;

    @Column(onInsertValue = "0")
    private Long acceptedNumber;

    private Integer createdById;

    private String displayId;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject statisticInfo;

    private Integer totalScore;

    private Integer majorTagId;

    private Integer subTagId;
}