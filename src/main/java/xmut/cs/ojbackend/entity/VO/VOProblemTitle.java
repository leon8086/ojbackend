package xmut.cs.ojbackend.entity.VO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xmut.cs.ojbackend.entity.ProblemTag;

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

    @Column(value = "_id")
    private String displayId;

    private Integer contestId;

    @RelationManyToMany(
            joinTable = "problem_tags",
            selfField = "id",
            joinSelfColumn="problem_id",
            targetField = "id",
            joinTargetColumn="problemtag_id"
    )
    private List<ProblemTag> tags;
}