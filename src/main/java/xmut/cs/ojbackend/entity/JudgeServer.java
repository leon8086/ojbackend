package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
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
 * @since 2024-06-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "judge_server")
public class JudgeServer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String hostname;

    private String ip;

    private String judgerVersion;

    private Integer cpuCore;

    private Double memoryUsage;

    private Double cpuUsage;

    private Date lastHeartbeat;

    private Integer taskNumber;

    private String serviceUrl;

    private Boolean isDisabled;

}
