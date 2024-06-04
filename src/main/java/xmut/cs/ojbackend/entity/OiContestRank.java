package xmut.cs.ojbackend.entity;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;

import java.io.Serializable;

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
@Table(value = "oi_contest_rank")
public class OiContestRank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    @Column(onInsertValue = "0")
    private Integer submissionNumber;

    @Column(onInsertValue = "0")
    private Integer totalScore;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject submissionInfo;

    private Integer contestId;

    private Integer userId;

    @RelationOneToOne(selfField = "userId", targetField = "id")
    private User user;

    public OiContestRank(Integer contestId, Integer userId) {
        this.contestId = contestId;
        this.userId = userId;
        this.submissionNumber = 0;
        this.totalScore = 0;
        this.submissionInfo = new JSONObject();
    }
}
