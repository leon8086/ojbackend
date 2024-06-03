package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(value = "submission")
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Integer contestId;

    private Integer problemId;

    private Timestamp createTime;

    private Integer userId;

    private String code;

    private Integer result;

    private String info;

    private String language;

    private Boolean shared;

    private String statisticInfo;

    private String username;

    private String ip;

}
