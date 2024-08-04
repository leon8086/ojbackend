package xmut.cs.ojbackend.entity.DTO;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class DTOProblemImport implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;

    private String description;

    private String inputDescription;

    private String outputDescription;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray samples;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray testCaseScore;

    private String hint;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private List<String> languages;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject template;

    private Integer timeLimit;

    private Integer memoryLimit;

    private String difficulty;

    private String source;

    private Integer createdById;

    private String displayId;

    private Integer totalScore;

    private String majorTag;

    private String subTag;
}