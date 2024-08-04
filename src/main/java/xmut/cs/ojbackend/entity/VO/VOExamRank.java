package xmut.cs.ojbackend.entity.VO;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xmut.cs.ojbackend.entity.User;

import java.io.Serial;
import java.io.Serializable;

/**
 *  实体类。
 *
 * @author leon
 * @since 2024-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "exam_profile")
public class VOExamRank implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    private Integer examId;

    private Integer userId;

    @RelationOneToOne( selfField = "userId", targetField = "id")
    private User user;

    private Integer score;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray problemConfig;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONObject info;

    private Boolean isEnded;
}
