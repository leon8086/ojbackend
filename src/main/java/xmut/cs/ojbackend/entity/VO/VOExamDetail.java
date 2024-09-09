package xmut.cs.ojbackend.entity.VO;


import com.alibaba.fastjson2.JSONArray;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
public class VOExamDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private Integer createdById;

    private Integer courseId;

    @RelationOneToOne( selfField = "courseId", targetTable = "course", targetField = "id", valueField = "courseName")
    private String course;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray allowedIpRanges;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray problemConfig;

    private Boolean isEnded;

    private Integer problemCount;
}
