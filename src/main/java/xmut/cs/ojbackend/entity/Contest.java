package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
@Table(value = "contest")
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String description;

    private Boolean realTimeRank;

    private String password;

    private String ruleType;

    private Timestamp startTime;

    private Timestamp endTime;

    private Timestamp createTime;

    private Timestamp lastUpdateTime;

    private Boolean visible;

    private Integer createdById;

    private String allowedIpRanges;

}
