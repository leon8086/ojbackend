package xmut.cs.ojbackend.entity;


import com.alibaba.fastjson2.JSONArray;
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
import java.sql.Timestamp;

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
@Table(value = "exam")
public class Exam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private Timestamp startTime;

    private Timestamp endTime;

    private Timestamp createTime;

    private Timestamp lastUpdateTime;

    private Boolean visible;

    private Integer createdById;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray allowedIpRanges;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray problemConfig;
}
