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

/**
 *  实体类。
 *
 * @author leon
 * @since 2024-08-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "score")
public class Score implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private Integer userId;

    private Integer majorTag;

    private Integer score;
}
