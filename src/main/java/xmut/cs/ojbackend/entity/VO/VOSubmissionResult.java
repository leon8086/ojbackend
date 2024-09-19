package xmut.cs.ojbackend.entity.VO;

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
public class VOSubmissionResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer problemId;

    private Integer userId;

    @Column(onInsertValue = "6")
    private Integer result;

    @Column(onInsertValue = "'{}'", typeHandler = Fastjson2TypeHandler.class)
    private JSONObject info;
}
