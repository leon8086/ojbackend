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
@Table(value = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject acmProblemsStatus;

    @Column(onInsertValue = "/public/avatar/default.png")
    private String avatar;

    private String blog;

    private String mood;

    @Column(onInsertValue = "0")
    private Integer acceptedNumber;

    @Column(onInsertValue = "0")
    private Integer submissionNumber;

    private String github;

    private String school;

    private String major;

    private Integer userId;

    @Column(onInsertValue = "0")
    private Long totalScore;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject oiProblemsStatus;

    private String realName;

    private String language;

    //@RelationOneToOne(selfField = "userId", targetField = "id")
    //private User user;

    public void addScore(int score) {
        this.totalScore += score;
    }

    public void addScore(int curScore, int lastScore) {
        this.totalScore -= lastScore;
        this.totalScore += curScore;
    }
}
