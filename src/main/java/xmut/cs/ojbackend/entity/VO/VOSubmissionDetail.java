package xmut.cs.ojbackend.entity.VO;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
@Table(value = "submission")
public class VOSubmissionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    private Integer contestId;

    private Integer problemId;

    @Column(ignore = true)
    private String problemDisplayId;

    private Date createTime;

    private Integer userId;

    private String code;

    @Column(onInsertValue = "6")
    private Integer result;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject info;

    private String language;

    @Column(onInsertValue = "false")
    private Boolean shared;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject statisticInfo;

    private String username;

    private String ip;

    @RelationOneToOne(selfField="problemId", targetField="id")
    private VOProblemDetail voProblemDetail;
}
